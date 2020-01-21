package com.example.demo.controller;

import java.io.Console;
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
import com.example.demo.utils.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	ObjectMapper mapper = new ObjectMapper();
	String json = "{\"color\":\"Black\",\"type\":\"BMW\",\"test\":\"\",\"employee\":{\"em1\":\"x\",\"answer\":null}}";

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

	// Get with config //USE
	@RequestMapping("/test5")
	public String test5() throws IOException {
		String rawConfig = "employee.em1";
		List<String> configs = ConvertConfigsToList(rawConfig);

		JsonNode mainNode = mapper.readTree(json);
		JsonNode toDateNode = mainNode;
		for (String config : configs) {
			toDateNode = toDateNode.get(config);

		}

		return toDateNode.asText();
	}

	// Set
	@RequestMapping("/test6")
	public String test6() throws IOException {
		String newString = "{\"nick\": \"cowtowncoder\"}";
		JsonNode newNode = mapper.readTree(newString);

		ObjectNode example = mapper.createObjectNode();
		example.put("base", "test");
		example.set("name", newNode);
		example.put("price", "100");

		return mapper.writeValueAsString(example);
	}

	// Set with config hardcode
	@RequestMapping("/test7")
	public String test7() throws IOException {

		String json = "{\"color\":\"Black\",\"type\":\"BMW\",\"test\":\"\",\"employee\":{\"em1\":\"x\",\"answer\":null}}";

		ObjectNode rootNode = (ObjectNode) mapper.readTree(json);

		rootNode.with("employee").put("em1", 1);

		return mapper.writeValueAsString(rootNode);
	}

	// Set with config //USE
	@RequestMapping("/test8")
	public String test8() throws IOException {
		String json = "{\"color\":\"Black\",\"type\":\"BMW\",\"test\":\"\",\"employee\":{\"em1\":\"x\",\"answer\":null}}";
		ObjectNode initNode = (ObjectNode) mapper.readTree(json);

		String configDestination = "employee.answer";
		String[] configs = ConvertConfigsToArray(configDestination);

		ObjectNode lastNode = initNode;

		// skip last value
		for (int i = 0; i < configs.length - 1; i++) {
			lastNode = lastNode.with(configs[i]);
		}

		lastNode.put(configs[configs.length - 1], "newValue");

		return mapper.writeValueAsString(initNode);
	}

	@RequestMapping("/test")
	public String test() throws IOException {
		String json = "{\"color\":\"Black\",\"type\":\"BMW\",\"test\":\"\",\"employee\":{\"em1\":\"x\",\"answer\":null}}";

		ObjectNode rootNode = (ObjectNode) mapper.readTree(json);

		rootNode.with("employee").put("em1", 1);

		return mapper.writeValueAsString(rootNode);
	}

	private List<String> ConvertConfigsToList(String configs) {
		String[] arryConfig = configs.split("\\.");
		List<String> result = Arrays.asList(arryConfig);
		return result;
	}

	private String[] ConvertConfigsToArray(String configs) {
		String[] arryConfig = configs.split("\\.");
		return arryConfig;
	}
}