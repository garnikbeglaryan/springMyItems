package com.example.springmyitems.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double price;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToMany
    private List<Category> categories;
    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages;


}
