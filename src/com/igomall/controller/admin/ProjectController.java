
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
import com.igomall.entity.Project;
import com.igomall.service.ProjectService;

/**
 * Controller - 項目
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("adminProjectController")
@RequestMapping("/admin/project")
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService projectService;

	/**
	 * 检查項目名是否存在
	 */
	@GetMapping("/check_name")
	public @ResponseBody boolean checkName(String name) {
		return StringUtils.isNotEmpty(name) && !projectService.nameExists(name);
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		return "admin/project/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public String save(Project project, RedirectAttributes redirectAttributes) {
		if (!isValid(project, BaseEntity.Save.class)) {
			return ERROR_VIEW;
		}
		if (projectService.nameExists(project.getName())) {
			return ERROR_VIEW;
		}
		projectService.save(project);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("project", projectService.find(id));
		return "admin/project/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Project project, RedirectAttributes redirectAttributes) {
		if (!isValid(project)) {
			return ERROR_VIEW;
		}
		projectService.update(project);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", projectService.findPage(pageable));
		return "admin/project/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public @ResponseBody Message delete(Long[] ids) {
		projectService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}