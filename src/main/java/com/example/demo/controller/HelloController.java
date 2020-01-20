package com.example.demo.controller;

import java.io.File;
import java.io.IOException;

import com.example.demo.model.Car;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	ObjectMapper objectMapper;

	public HelloController() {
		this.objectMapper = new ObjectMapper();
	}

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/test")
	public String test() throws JsonGenerationException, JsonMappingException, IOException {
		Car car = new Car("yellow", "renault");
		objectMapper.writeValue(new File("target/car.json"), car);
		return "";
	}

	@RequestMapping("/test2")
	public String test2() throws IOException {
		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		JsonNode jsonNode = objectMapper.readTree(json);
		String color = jsonNode.get("color").asText();
		return color;
	}
}