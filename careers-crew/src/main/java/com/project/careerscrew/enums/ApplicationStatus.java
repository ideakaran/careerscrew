package com.project.careerscrew.enums;

public enum ApplicationStatus {
    SUBMITTED("submitted"),
    PENDING("pending"),
    RECEIVED("received"),
    INTERVIEW_SCHEDULED("interviewscheduled"),
    SELECTED("selected"),
    UNSELECTED("unselected");

    private String status;

    ApplicationStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
