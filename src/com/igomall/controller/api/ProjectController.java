
package com.igomall.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.igomall.entity.Project;
import com.igomall.service.ProjectService;

/**
 * Controller - 項目
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("apiProjectController")
@RequestMapping("/api/project")
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService projectService;

	/**
	 * 检查項目名是否存在
	 */
	@PostMapping("/check_name")
	public @ResponseBody boolean checkName(String name) {
		return StringUtils.isNotEmpty(name) && !projectService.nameExists(name);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Project project) {
		if (!isValid(project, BaseEntity.Save.class)) {
			return ERROR_MESSAGE;
		}
		if (project.isNew()&&projectService.nameExists(project.getName())) {
			return Message.error("項目名稱已存在！");
		}
		if(project.isNew()) {
			projectService.save(project);
		}else {
			projectService.update(project);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	public Project edit(Long id, ModelMap model) {
		return projectService.find(id);
	}
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Project.ListView.class)
	public Page<Project> list(Pageable pageable) {
		return projectService.findPage(pageable);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		projectService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 列表
	 */
	@PostMapping("/loadList")
	public List<Map<String,Object>> loadList() {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		List<Project> projects = projectService.findAll();
		for (Project project : projects) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", project.getId());
			map.put("name", project.getName());
			data.add(map);
		}
		
		return data;
		
	}
}