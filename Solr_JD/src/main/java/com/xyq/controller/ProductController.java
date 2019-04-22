package com.xyq.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyq.entity.Page;
import com.xyq.entity.Parameter;
import com.xyq.service.ProductService;

@Controller
public class ProductController {

	@Resource
	private ProductService productService;
	
	@RequestMapping("jd")
	public String getProduct(Parameter parameter, Model model) {
		Page page = productService.getProductInfo(parameter);
		model.addAttribute("page", page);
		return "forward:/index.jsp";
	}
}
