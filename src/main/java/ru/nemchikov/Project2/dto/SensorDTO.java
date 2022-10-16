package ru.nemchikov.Project2.dto;

import javax.validation.constraints.NotEmpty;

public class SensorDTO {

    @NotEmpty(message = "Name should be not empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
