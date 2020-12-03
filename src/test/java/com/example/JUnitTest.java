package com.example;

import java.util.*;

import com.example.demo.UserController;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import org.json.*;

@SpringBootTest
public class JUnitTest{

    @Test
    public void testNestedWeatherJSON() {
        String caseTest = "{\"weather\":[{\"main\":\"Clouds\"}]}";
        String target = "main";
        String expected = "Clouds";
        UserController testClass = new UserController();
        assertTrue(expected.equals(testClass.GetNestedWeatherJSON(caseTest, target)));
    }

    @Test
    public void testNestedTemperature() {
        String caseTest = "{\"main\":{\"temp\":37.4}}";
        String target = "temp";
        Double expected = 37.4;
        UserController testClass = new UserController();
        assertTrue(expected == testClass.GetNestedTempJSON(caseTest, target));
    }
}