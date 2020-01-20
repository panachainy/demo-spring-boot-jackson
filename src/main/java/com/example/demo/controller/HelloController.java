package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.example.demo.model.Car;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	ObjectMapper mapper = new ObjectMapper();
	String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" , \"test\" : \"\" , \"employee\" : {\"em1\":\"x\"} }";

	public HelloController() {
	}

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/test1")
	public String test1() throws JsonGenerationException, JsonMappingException, IOException {
		Car car = new Car("yellow", "renault");
		mapper.writeValue(new File("target/car.json"), car);
		return "target/car.json";
	}

	@RequestMapping("/test2")
	public String test2() throws IOException {
		JsonNode jsonNode = mapper.readTree(json);
		String color = jsonNode.get("color").asText();
		return color;
	}

	@RequestMapping("/test3")
	public String test3() {
		ArrayNode arrayNode = mapper.createArrayNode();

		ObjectNode example = mapper.createObjectNode();
		example.put("bookName", "Java");
		example.put("price", "100");

		arrayNode.add(example);

		return arrayNode.toString();
	}

	@RequestMapping("/test4")
	public String test4() {
		ObjectNode main = mapper.createObjectNode();
		ArrayNode arrayNode = mapper.createArrayNode();

		ObjectNode example = mapper.createObjectNode();
		example.put("bookName", "Java");
		example.put("price", "100");

		arrayNode.add(example);

		main.set("x", arrayNode);
		main.put("test", "book");

		return main.toString();
	}

	@RequestMapping("/test5")
	public String test5() throws IOException {
		String config1 = "employee.em1";
		String[] arryConfig = config1.split("\\.");
		List<String> configs = Arrays.asList(arryConfig);

		JsonNode mainNode = mapper.readTree(json);
		JsonNode toDateNode = mainNode;
		for (String config : configs) {
			toDateNode = toDateNode.get(config);

		}
		return toDateNode.asText();
	}

}