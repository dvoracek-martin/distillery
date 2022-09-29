package com.dvoracek.distillery.kafka.config.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic distillationPhaseCreatedTopic() {
        return TopicBuilder.name("distillation-phase-created").build();
    }

    @Bean
    public NewTopic distillationPlanCreatedTopic() {
        return TopicBuilder.name("distillation-plan-created").build();
    }

    @Bean
    public NewTopic distillationPhaseEditedTopic() {
        return TopicBuilder.name("distillation-phase-edited").build();
    }

    @Bean
    public NewTopic distillationPlanEditedTopic() {
        return TopicBuilder.name("distillation-plan-edited").build();
    }

    @Bean
    public NewTopic distillationPhaseDeletedTopic() {
        return TopicBuilder.name("distillation-phase-deleted").build();
    }

    @Bean
    public NewTopic distillationPlanDeletedTopic() {
        return TopicBuilder.name("distillation-plan-deleted").build();
    }

    @Bean
    public NewTopic distillationStartedTopic() {
        return TopicBuilder.name("distillation-started").build();
    }

    @Bean
    public NewTopic distillationTerminatedTopic() {
        return TopicBuilder.name("distillation-terminated").build();
    }

    @Bean
    public NewTopic distillationPausedTopic() {
        return TopicBuilder.name("distillation-paused").build();
    }

    @Bean
    public NewTopic distillationContinuedTopic() {
        return TopicBuilder.name("distillation-continued").build();
    }

    @Bean
    public NewTopic distillationNextPhaseTopic() {
        return TopicBuilder.name("distillation-next-phase").build();
    }

    @Bean
    public NewTopic distillationProgressBackendTopic() {return TopicBuilder.name("distillation-progress-backend").build();}
    @Bean
    public NewTopic distillationProgressRaspberryTopic() {return TopicBuilder.name("distillation-progress-raspberry").build();}
}