
package com.igomall.controller.api;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.Message;
import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Module;
import com.igomall.entity.Property;
import com.igomall.service.ModuleService;
import com.igomall.service.PropertyService;

/**
 * Controller - 項目
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("apiPropertyController")
@RequestMapping("/api/property")
public class PropertyController extends BaseController {

	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private ModuleService moduleService;

	/**
	 * 检查項目名是否存在
	 */
	@PostMapping("/check_name")
	public @ResponseBody boolean checkName(String name) {
		return StringUtils.isNotEmpty(name) && !propertyService.nameExists(name);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Property property,Long moduleId) {
		property.setModule(moduleService.find(moduleId));
		if (!isValid(property, BaseEntity.Save.class)) {
			return ERROR_MESSAGE;
		}
		if (property.isNew()&&propertyService.nameExists(property.getName())) {
			return Message.error("属性名稱已存在！");
		}
		if(property.isNew()) {
			propertyService.save(property);
		}else {
			propertyService.update(property);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(Property.EditView.class)
	public Property edit(Long id, ModelMap model) {
		return propertyService.find(id);
	}
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Property.ListView.class)
	public Page<Property> list(Pageable pageable,Long moduleId) {
		Module module = moduleService.find(moduleId);
		return propertyService.findPage(pageable,module);
	}
	
	@PostMapping("/list1")
	@JsonView(Property.ListView.class)
	public List<Property> list1(Long moduleId) {
		Module module = moduleService.find(moduleId);
		return propertyService.findList(module);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		propertyService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}