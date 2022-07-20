package com.example.springmyitems.controller;

import com.example.springmyitems.dto.CreateItemRequest;
import com.example.springmyitems.entity.Item;
import com.example.springmyitems.entity.User;
import com.example.springmyitems.security.CurrentUser;
import com.example.springmyitems.service.CategoryService;
import com.example.springmyitems.service.ItemService;
import com.example.springmyitems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController extends HttpServlet {

    private final ItemService itemService;
    private final UserService userService;
    private final CategoryService categoryService;

    private final ModelMapper mapper;


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
    public String addItem(@ModelAttribute @Valid CreateItemRequest createItemRequest,
                          BindingResult bindingResult,
                          @RequestParam("pictures") MultipartFile[] uploadedFiles,
                          @AuthenticationPrincipal CurrentUser currentUser, ModelMap map) throws IOException {


        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            map.addAttribute("categories", categoryService.findAll());
            return "saveItem";

        } else {
            Item item = mapper.map(createItemRequest, Item.class);
            itemService.addItem(item, uploadedFiles, currentUser.getUser(), createItemRequest.getCategories());
        }
        return "redirect:/items";
    }

    @GetMapping("/items/{id}")
    public String singleItem(@PathVariable int id, ModelMap map) {
        map.addAttribute("categories", categoryService.findAll());
        map.addAttribute("item", itemService.findById(id));
        return "singleItem";
    }
}
