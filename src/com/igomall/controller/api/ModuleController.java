
package com.igomall.controller.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.Message;
import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.Template;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Module;
import com.igomall.entity.Project;
import com.igomall.service.ModuleService;
import com.igomall.service.ProjectService;
import com.igomall.service.StaticService;
import com.igomall.service.TemplateService;
import com.igomall.util.CompressUtils;
import com.igomall.util.FreeMarkerUtils;

import freemarker.template.TemplateException;

/**
 * Controller - 項目
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("apiModuleController")
@RequestMapping("/api/module")
public class ModuleController extends BaseController {

	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private StaticService staticService;

	/**
	 * 检查項目名是否存在
	 */
	@PostMapping("/check_name")
	public @ResponseBody boolean checkName(String name) {
		return StringUtils.isNotEmpty(name) && !moduleService.nameExists(name);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(Module module,Long projectId) {
		module.setProject(projectService.find(projectId));
		if (!isValid(module, BaseEntity.Save.class)) {
			return ERROR_MESSAGE;
		}
		if (module.isNew()&&moduleService.nameExists(module.getName())) {
			return Message.error("模块名稱已存在！");
		}
		if(module.isNew()) {
			moduleService.save(module);
		}else {
			moduleService.update(module);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 编辑
	 */
	@PostMapping("/edit")
	@JsonView(Module.EditView.class)
	public Module edit(Long id, ModelMap model) {
		return moduleService.find(id);
	}
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	@JsonView(Module.ListView.class)
	public Page<Module> list(Pageable pageable,Long projectId) {
		Project project = projectService.find(projectId);
		return moduleService.findPage(pageable,project);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		moduleService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	@PostMapping("/download")
    public ResponseEntity<byte[]> download(Long moduleId) throws IOException, TemplateException{
        Module module = moduleService.find(moduleId);
        String filename=module.getName()+".zip";
        Template template = templateService.get("entity");
		Map<String, Object> model = new HashMap<>();
		model.put("name",module.getName());
		model.put("module",module);
		String staticPath = FreeMarkerUtils.process(template.getStaticPath(), model);
		String filePath = staticService.build1(template.getTemplatePath(), staticPath, model);
		//将生成的文件进行压缩
		File destFile = new File("3.zip");
		CompressUtils.archive(new File(filePath), destFile, "zip");
		
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
        headers.setContentDispositionFormData("attachment", filename);    
        headers.set("Access-Control-Expose-Headers", "Content-Disposition");
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile),  headers, HttpStatus.CREATED); 
    }
}