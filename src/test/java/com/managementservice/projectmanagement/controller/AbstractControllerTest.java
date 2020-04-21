package com.managementservice.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mMockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    protected <T> List<T> mapFromJsonToList(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
    }
}
