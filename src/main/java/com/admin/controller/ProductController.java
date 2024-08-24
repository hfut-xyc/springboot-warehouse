package com.admin.controller;

import com.admin.entity.Product;
import com.admin.service.ProductService;
import com.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/list")
    public Result list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        Map<String, Object> map  = productService.listProduct(keyword);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Integer addProduct(@RequestBody Product product) throws Exception {
        return productService.insertProduct(product);
    }
}
