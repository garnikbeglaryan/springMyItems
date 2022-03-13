package com.example.springmyitems.repository;

import com.example.springmyitems.entity.Item;
import com.example.springmyitems.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByUser(User user);
}
