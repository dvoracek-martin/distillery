package com.dvoracek.distillery.distillation.procedure.service.internal;

public class ElasticsearchIOException extends RuntimeException {
    public ElasticsearchIOException() {
        super("There was a IO problem with the ES");
    }
}
