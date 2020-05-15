package org.server.controller;

import io.swagger.annotations.ApiOperation;
import org.server.entity.Product;
import org.server.entity.User;
import org.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;


}
