package com.example.springmyitems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {


    private int id;
    @Size(min = 2, max = 40, message = "Title length should be between 2 and 40")
    private String title;
    @DecimalMin(value = "1", message = "price should be > 1")
    private double price;
    @NotEmpty(message = "description should not be empty")
    private String description;
    private List<Integer> categories;
}
