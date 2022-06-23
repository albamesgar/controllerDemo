package com.ironhack.controllerDemo.controller.interfaces;

import com.ironhack.controllerDemo.model.Course;

import java.util.List;

public interface CourseController {
    List<Course> findAll();
    Course findById(String code);
    Course store(Course course);

    void update(String code, Course course);
    void delete(String code);
}
