package com.example.springmyitems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {


    private int id;
    private String title;
    private double price;
    private String description;
    private List<Integer> categories;
}
