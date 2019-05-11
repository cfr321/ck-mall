package com.cako.item.web;

import com.cako.item.pojo.Category;
import com.cako.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categotyService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid){
        return ResponseEntity.ok(categotyService.queryCategoryListByPid(pid));
    }
    //"/item/category/bid/" + oldBrand.id
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryListByBid(@PathVariable("bid") Long bid){
        return ResponseEntity.ok(categotyService.queryCategoryListByBid(bid));
    }
}
