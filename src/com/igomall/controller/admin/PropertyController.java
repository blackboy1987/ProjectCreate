
package com.igomall.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.Message;
import com.igomall.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Property;
import com.igomall.service.PropertyService;

/**
 * Controller - 項目
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("adminPropertyController")
@RequestMapping("/admin/property")
public class PropertyController extends BaseController {

	@Autowired
	private PropertyService propertyService;

	/**
	 * 检查項目名是否存在
	 */
	@GetMapping("/check_name")
	public @ResponseBody boolean checkName(String name) {
		return StringUtils.isNotEmpty(name) && !propertyService.nameExists(name);
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/property/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(Property property, RedirectAttributes redirectAttributes) {
		if (!isValid(property, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		if (propertyService.nameExists(property.getName())) {
			return ERROR_VIEW;
		}
		propertyService.save(property);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("property", propertyService.find(id));
		return "admin/property/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Property property, RedirectAttributes redirectAttributes) {
		if (!isValid(property)) {
			return ERROR_VIEW;
		}
		propertyService.update(property);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", propertyService.findPage(pageable));
		return "admin/property/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		propertyService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}