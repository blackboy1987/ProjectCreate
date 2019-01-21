
package com.igomall.controller.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

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
import org.springframework.web.context.ServletContextAware;

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
public class ModuleController extends BaseController implements ServletContextAware {

	private ServletContext servletContext;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private StaticService staticService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
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
        Template entity = templateService.get("entity");
        Template dao = templateService.get("dao");
        Template daoImpl = templateService.get("daoImpl");
        Template service = templateService.get("service");
        Template serviceImpl = templateService.get("serviceImpl");
        Template controller = templateService.get("controller");
        Template addPage = templateService.get("addPage");
        Template listPage = templateService.get("listPage");
        

        Template modulePage = templateService.get("module");
        Template servicesPage = templateService.get("services");
        
        
        
		Map<String, Object> model = new HashMap<>();
		model.put("name",StringUtils.capitalize(module.getName()));
		model.put("module",module);
		model.put("project",module.getProject());
		
		String rootPath = module.getProject().getName()+"/";
		String packagePath = module.getProject().getPackageName().replaceAll("\\.", "/")+"/";
		
		
		String staticPathEntity = FreeMarkerUtils.process(entity.getStaticPath(), model);
		String filePathEntity = staticService.build1(entity.getTemplatePath(), rootPath+packagePath+staticPathEntity, model);
		
		String staticPathDao = FreeMarkerUtils.process(dao.getStaticPath(), model);
		String filePathDao = staticService.build1(dao.getTemplatePath(), rootPath+packagePath+staticPathDao, model);
		
		String staticPathDaoImpl = FreeMarkerUtils.process(daoImpl.getStaticPath(), model);
		String filePathDaoImpl = staticService.build1(daoImpl.getTemplatePath(), rootPath+packagePath+staticPathDaoImpl, model);
		
		String staticPathService = FreeMarkerUtils.process(service.getStaticPath(), model);
		String filePathService = staticService.build1(service.getTemplatePath(), rootPath+packagePath+staticPathService, model);
		
		String staticPathServiceImpl = FreeMarkerUtils.process(serviceImpl.getStaticPath(), model);
		String filePathServiceImpl = staticService.build1(serviceImpl.getTemplatePath(), rootPath+packagePath+staticPathServiceImpl, model);
		
		String staticPathController = FreeMarkerUtils.process(controller.getStaticPath(), model);
		String filePathController = staticService.build1(controller.getTemplatePath(), rootPath+packagePath+staticPathController, model);
		
		String staticPathAdd = FreeMarkerUtils.process(addPage.getStaticPath(), model);
		String filePathAdd = staticService.build1(addPage.getTemplatePath(), rootPath+staticPathAdd, model);
		
		String staticPathList = FreeMarkerUtils.process(listPage.getStaticPath(), model);
		String filePathList = staticService.build1(listPage.getTemplatePath(), rootPath+staticPathList, model);

		String staticPathModule = FreeMarkerUtils.process(modulePage.getStaticPath(), model);
		String filePathModule = staticService.build1(modulePage.getTemplatePath(), rootPath+staticPathModule, model);
		
		String staticPathServices = FreeMarkerUtils.process(servicesPage.getStaticPath(), model);
		String filePathListServices = staticService.build1(servicesPage.getTemplatePath(), rootPath+staticPathServices, model);
		
		//将生成的文件进行压缩
		File destFile = new File("3.zip");
//		CompressUtils.archive(new File[] {
//				new File(filePathEntity),
//				new File(filePathDao),
//				new File(filePathDaoImpl),
//				new File(filePathService),
//				new File(filePathServiceImpl),
//				new File(filePathController),
//				new File(filePathAdd),
//				new File(filePathList),
//				new File(filePathModule),
//				new File(filePathListServices),
//		}, destFile, "zip");
		
		CompressUtils.archive(new File(servletContext.getRealPath(rootPath)), destFile, "zip");
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
        headers.setContentDispositionFormData("attachment", filename);    
        headers.set("Access-Control-Expose-Headers", "Content-Disposition");
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile),  headers, HttpStatus.CREATED); 
    }
}