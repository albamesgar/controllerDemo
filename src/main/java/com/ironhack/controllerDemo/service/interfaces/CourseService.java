package com.ironhack.controllerDemo.service.interfaces;

import com.ironhack.controllerDemo.model.Course;

public interface CourseService {
    void update(String code, Course course);
    void delete(String code);
}
