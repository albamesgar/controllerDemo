package com.ironhack.controllerDemo.model;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Course {
    @Id
    @Column(name = "course_code")
    @NotNull
    private String code;
    @Column(name = "course_name")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    public Course() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
