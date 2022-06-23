package com.ironhack.controllerDemo.service.impl;

import com.ironhack.controllerDemo.model.Course;
import com.ironhack.controllerDemo.repository.CourseRepository;
import com.ironhack.controllerDemo.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public void update(String code, Course course) {
        Optional<Course> optionalCourse = courseRepository.findById(code);
        if (!optionalCourse.isPresent()){
            //Lanzar error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not fount");
        }
        //Update record
        course.setCode(code);
        courseRepository.save(course);
    }

    public void delete(String code) {
        Course course = courseRepository.findById(code).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
//        courseRepository.delete(course);
        courseRepository.deleteById(code);
    }
}
