package com.mvp.esm.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse {
    private String message;
    private Object results;

    public RestResponse(String message) {
        this.message = message;
    }

    public RestResponse(Object results) {
        this.results = results;
    }

    public RestResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }
}
