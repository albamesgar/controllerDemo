package com.ironhack.controllerDemo.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.controllerDemo.model.Course;
import com.ironhack.controllerDemo.repository.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CourseControllerImplTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private WebApplicationContext webApplicationContext; //Permite trabajar con los controllers
    private MockMvc mockMvc; //Simular peticiones HTTP
    private final ObjectMapper objectMapper = new ObjectMapper(); //Construir objetos JSON a partir de clases de JAVA

    private Course course1,course2;

    @BeforeEach
    void setUp() {
        //Inicializar el MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //Llamamos al builders que es una clase que sirve para construir objetos MoclMvc

        //Guardamos la data por defecto
        course1 = new Course("CS101", "Intro to Java");
        course2 = new Course("CS103", "Databases");
        courseRepository.saveAll(List.of(course1,course2));
    }

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    void findAll_NoParams_AllCourses() throws Exception {
        // Llamar con el GET a /courses
        // Comprobamos que el status code de  respuesta sea OK
        // Comprobamos que la respuesta esté en gormato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn(); //Para cerrar la petición
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Intro to Java"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Databases"));
    }

    @Test
    void findById() throws Exception {
        // Llamar con el GET a /courses/{code}
        // Comprobamos que el status code de la respuesta sea OK
        // Comprobamos que la respuesta esté en formato JSON
        // Comprobamos que el resultado es el que toca
        MvcResult mvcResult = mockMvc.perform(get("/courses/"+course1.getCode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Intro to Java"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Databases"));
    }

    @Test
    void store() throws Exception {
        // Llamar con el POST a /courses
        // Comprobamos que el status code de respuesta sea CREATED
        // Comprobamos que la respuesta está en formato JSON
        // Comprobamos que el resultado es el que toca

        // Preparo el curso que voy a insertar
        Course course = new Course("CS105","Intro to Testing");
        String body = objectMapper.writeValueAsString(course);

        // Hago la llamada HTTP
        MvcResult mvcResult = mockMvc.perform(
                post("/courses")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        // Compruebo el formato de la respuesta
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CS105"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Intro to Testing"));
        // Compruebo que se haya guardado en la base de datos
        assertTrue(courseRepository.existsById(course.getCode()));
        // Si id es auto_increment compruebo que el size haya aumentado
    }

    @Test
    void update() throws Exception {
        // Llamar con el PUT a /courses/{code}
        // Comprobamos que el status code de respuesta sea NO_CONTENT
        // Comprobamos que el registro se ha actualizado correctamente

        Course course = new Course("CS101","Intro to Java2");
        String body = objectMapper.writeValueAsString(course);

        MvcResult mvcResult = mockMvc.perform(
                put("/courses/"+course1.getCode())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent())
                .andReturn();
        Optional<Course> optionalCourse = courseRepository.findById(course1.getCode());
        assertTrue(optionalCourse.isPresent());
        assertEquals("Intro to Java2",optionalCourse.get().getName());
    }

    @Test
    void delete_valid_CourseRemoved() throws Exception {
        // Llamar con el DELETE a /courses/{code}
        // Comprobamos que el status code de respuesta sea NO_CONTENT
        // Comprobamos que el registro se no existe

        MvcResult mvcResult = mockMvc.perform(
                delete("/courses/"+course1.getCode())
        ).andExpect(status().isNoContent())
                .andReturn();
        assertFalse(courseRepository.existsById(course1.getCode()));
    }
}