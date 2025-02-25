package com.workintech.s17d2.dto;

import com.workintech.s17d2.model.Developer;
import org.springframework.http.HttpStatus;

public class DeveloperResponse {
    private Developer developer;
    private HttpStatus status;
    private String message;

    public DeveloperResponse(Developer developer, HttpStatus status, String message) {
        this.developer = developer;
        this.status = status;
        this.message = message;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
