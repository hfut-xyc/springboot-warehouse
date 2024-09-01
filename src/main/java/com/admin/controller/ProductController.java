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
    public Result listProduct(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        Map<String, Object> map  = productService.selectListByName(keyword);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Result insert(@RequestBody Product product) throws Exception {
        Integer res = productService.insert(product);
        return Result.ok("添加成功", res);
    }

    @PutMapping("")
    public Result updateWarehouse(@RequestBody Product product) throws Exception {
        Integer res = productService.update(product);
        return Result.ok("修改成功", res);
    }

    @DeleteMapping("")
    public Result deleteWarehouse(@RequestParam Integer id) throws Exception {
        Integer res = productService.deleteById(id);
        return Result.ok("删除成功", res);
    }
}
