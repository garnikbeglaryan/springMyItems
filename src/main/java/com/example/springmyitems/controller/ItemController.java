package com.example.springmyitems.controller;

import com.example.springmyitems.dto.CreateItemRequest;
import com.example.springmyitems.entity.Item;
import com.example.springmyitems.entity.User;
import com.example.springmyitems.security.CurrentUser;
import com.example.springmyitems.service.CategoryService;
import com.example.springmyitems.service.ItemService;
import com.example.springmyitems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;
    private final CategoryService categoryService;


    @GetMapping("/items")
    public String itemsPage(ModelMap map) {
        List<Item> items = itemService.findAll();
        map.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/items/byUser/{id}")
    public String itemsByUserPage(ModelMap map, @PathVariable("id") int id) {
        User user = userService.findById(id);
        List<Item> items = itemService.findAllByUser(user);
        map.addAttribute("items", items);
        return "items";
    }


    @GetMapping("/myItems")
    public String myItems(@AuthenticationPrincipal CurrentUser currentUser, ModelMap map) {
        User user = currentUser.getUser();
        List<Item> items = itemService.findAllByUser(user);
        map.addAttribute("items", items);
        return "items";
    }


    @GetMapping("/items/add")
    public String addItemPage(ModelMap map) {
        map.addAttribute("categories", categoryService.findAll());
        map.addAttribute("users", userService.findAll());
        return "saveItem";
    }

    @PostMapping("/items/add")
    public String addItem(@ModelAttribute CreateItemRequest createItemRequest,
                          @RequestParam("pictures") MultipartFile[] uploadedFiles,
                          @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        itemService.addItemFromItemRequest(createItemRequest, uploadedFiles,currentUser.getUser());
        return "redirect:/items";
    }

    @GetMapping("/items/{id}")
    public String singleItem(@PathVariable int id, ModelMap map) {
        map.addAttribute("categories", categoryService.findAll());
        map.addAttribute("item", itemService.findById(id));
        return "singleItem";
    }
}
