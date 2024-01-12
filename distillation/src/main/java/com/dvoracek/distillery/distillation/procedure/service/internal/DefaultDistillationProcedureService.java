package com.dvoracek.distillery.distillation.procedure.service.internal;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import com.dvoracek.distillery.distillation.plan.service.DistillationPlanService;
import com.dvoracek.distillery.distillation.procedure.model.DistillationEndReason;
import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;
import com.dvoracek.distillery.distillation.procedure.repository.DistillationProcedureRepository;
import com.dvoracek.distillery.distillation.procedure.service.DistillationProcedureService;
import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataFromRaspiDto;
import jakarta.transaction.Transactional;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class DefaultDistillationProcedureService implements DistillationProcedureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationProcedureService.class);
    private final DistillationProcedureRepository distillationProcedureRepository;
    private final DistillationPlanService distillationPlanService;

    public DefaultDistillationProcedureService(DistillationProcedureRepository distillationProcedureRepository, DistillationPlanService distillationPlanService) {
        this.distillationProcedureRepository = distillationProcedureRepository;
        this.distillationPlanService = distillationPlanService;
    }

    @Override
    public DistillationProcedure getDistillationProcedure(Long procedureId) {
        return distillationProcedureRepository.findById(procedureId).orElseThrow(() -> new DistillationProcedureNotFoundException(procedureId));
    }

    @Override
    public DistillationProcedure getByPlanIdAndAttemptNumber(Long planId, int attemptNumber) {
        return distillationProcedureRepository.findByPlanIdAndAttemptNumber(planId, attemptNumber);
    }

    @Override
    public DistillationProcedure getLastByPlan(Long planId) {
        return distillationProcedureRepository.findLastByPlan(planId);
    }

    @Override
    public List<DistillationProcedure> getAll() {
        return distillationProcedureRepository.findAll().stream().sorted(comparing(DistillationProcedure::getId)).toList();
    }

    @Override
    public DistillationProcedure createDistillationProcedure(Long planId) {
        DistillationProcedure distillationProcedure = getLastByPlan(planId);
        DistillationProcedure currentDistillationProcedure = new DistillationProcedure();
        DistillationPlan distillationPlan = distillationPlanService.getDistillationPlan(planId);
        if (distillationProcedure == null) {
            distillationProcedure = new DistillationProcedure().setPlanId(distillationPlan.getId()).setPlanName(distillationPlan.getName()).setTimeStart(LocalDateTime.now()).setAttemptNumber(1).setEndReason(DistillationEndReason.NOT_DONE);
            currentDistillationProcedure = distillationProcedure;
        } else {
            currentDistillationProcedure.setPlanId(distillationPlan.getId()).setPlanName(distillationPlan.getName()).setTimeStart(LocalDateTime.now()).setAttemptNumber(distillationProcedure.getAttemptNumber() + 1).setEndReason(DistillationEndReason.NOT_DONE);
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Creating a new distillation procedure for plan ID: %d, attempt #%d", currentDistillationProcedure.getPlanId(), currentDistillationProcedure.getAttemptNumber()));
        }
        return distillationProcedureRepository.saveAndFlush(currentDistillationProcedure);
    }

    @Override
    public void terminateDistillationProcedure(Long procedureId) {
        DistillationProcedure distillationProcedure = getDistillationProcedure(procedureId);
        distillationProcedure.setTimeEnd(LocalDateTime.now());
        distillationProcedure.setEndReason(DistillationEndReason.DONE);
        distillationProcedureRepository.saveAndFlush(distillationProcedure);
    }

    @Override
    public void terminateDistillationProcedureByUser(Long procedureId) {
        DistillationProcedure distillationProcedure = getDistillationProcedure(procedureId);
        distillationProcedure.setTimeEnd(LocalDateTime.now());
        distillationProcedure.setEndReason(DistillationEndReason.TERMINATED_BY_USER);
        distillationProcedureRepository.saveAndFlush(distillationProcedure);
    }

    @Override
    public void deleteDistillationProcedure(Long procedureId) {
        DistillationProcedure distillationProcedure = distillationProcedureRepository.findById(procedureId).orElseThrow(() -> new DistillationProcedureNotFoundException(procedureId));
        distillationProcedureRepository.delete(distillationProcedure);
        LOGGER.info("Procedure deleted. ID: {}, plan Id: {}, attempt #: {}", distillationProcedure.getId(), distillationProcedure.getPlanId(), distillationProcedure.getAttemptNumber());
    }

    @Override
    public List<DistillationProcessDataFromRaspiDto> getDistillationProcedureFromES(Long procedureId) {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        SearchResponse<DistillationProcessDataFromRaspiDto> search;
        try {
            search = client.search(s ->
                            s.index("distillation-progress-raspberry")
                                    .query(q -> q.match(t -> t
                                            .field("distillationProcedureId")
                                            .query(procedureId)))
                                    .size(10000)
                                    .sort(so -> so
                                            .field(FieldSort.of(f -> f.field("timeStartedInMillis")
                                                    .order(SortOrder.Asc)))),
                    DistillationProcessDataFromRaspiDto.class);
        } catch (IOException e) {
            throw new ElasticsearchIOException();
        }
        List<DistillationProcessDataFromRaspiDto> distillationProcessDataFromRaspiDtos = new ArrayList<>();
        for (Hit<DistillationProcessDataFromRaspiDto> hit : search.hits().hits()) {
            distillationProcessDataFromRaspiDtos.add(hit.source());
        }
        return distillationProcessDataFromRaspiDtos;
    }
}
