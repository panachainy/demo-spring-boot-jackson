package com.example.demo.model;

import lombok.Data;

@Data
public class Car {
    public Car(String color, String type) {
        this.color = color;
        this.type = type;
    }

    private String color;
    private String type;
}