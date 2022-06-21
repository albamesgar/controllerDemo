package com.ironhack.controllerDemo.impl;

import com.ironhack.controllerDemo.interfaces.CourseController;
import com.ironhack.controllerDemo.model.Course;
import com.ironhack.controllerDemo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseControllerImpl implements CourseController {

    @Autowired
    private CourseRepository courseRespository;

    @GetMapping("/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> findAll() {
        return courseRespository.findAll();
    }

    @GetMapping("/courses/{code}")
    public Course findById(@PathVariable String code) {
        Optional<Course> optionalCourse = courseRespository.findById(code);
        return optionalCourse.get();
    }

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public Course store(@RequestBody @Valid Course course){
        return courseRespository.save(course);
    }
}
