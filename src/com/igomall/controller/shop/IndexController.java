
package com.igomall.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 首页
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("shopIndexController")
@RequestMapping("/")
public class IndexController {

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "redirect:/admin/index";
		//return "shop/index";
	}

}