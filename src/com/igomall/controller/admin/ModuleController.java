
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
import com.igomall.entity.Module;
import com.igomall.service.ModuleService;

/**
 * Controller - 項目
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("adminModuleController")
@RequestMapping("/admin/module")
public class ModuleController extends BaseController {

	@Autowired
	private ModuleService moduleService;

	/**
	 * 检查項目名是否存在
	 */
	@GetMapping("/check_name")
	public @ResponseBody boolean checkName(String name) {
		return StringUtils.isNotEmpty(name) && !moduleService.nameExists(name);
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/module/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(Module module, RedirectAttributes redirectAttributes) {
		if (!isValid(module, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		if (moduleService.nameExists(module.getName())) {
			return ERROR_VIEW;
		}
		moduleService.save(module);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("module", moduleService.find(id));
		return "admin/module/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Module module, RedirectAttributes redirectAttributes) {
		if (!isValid(module)) {
			return ERROR_VIEW;
		}
		moduleService.update(module);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", moduleService.findPage(pageable));
		return "admin/module/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		moduleService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}