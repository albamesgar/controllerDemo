package com.ironhack.controllerDemo.controller.impl;

import com.ironhack.controllerDemo.controller.interfaces.CourseController;
import com.ironhack.controllerDemo.model.Course;
import com.ironhack.controllerDemo.repository.CourseRepository;
import com.ironhack.controllerDemo.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseControllerImpl implements CourseController {

    @Autowired
    private CourseService courseService;

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

    @PutMapping("/courses/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //la petición se ejecutó de manera correcta pero no devuelve nada (204)
    public void update(@PathVariable String code, @RequestBody @Valid Course course) {
        courseService.update(code, course); //Lo paso a la capa de servicio por que tengo que aplicar una lógica
    }

    @DeleteMapping("/courses/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        courseService.delete(code);
    }
}
