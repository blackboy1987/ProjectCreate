
package com.igomall.controller.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igomall.Template;
import com.igomall.controller.admin.BaseController;
import com.igomall.service.StaticService;
import com.igomall.service.TemplateService;
import com.igomall.util.FreeMarkerUtils;

@RestController("commonStaticController")
@RequestMapping("/common/static")
public class StaticController extends BaseController {

	@Autowired
	private StaticService staticService;

	@Autowired
	private TemplateService templateService;

	@GetMapping("/build")
	public String build() throws Exception{
		Template template = templateService.get("entity");
		Map<String, Object> model = new HashMap<>();
		model.put("name","Project");
		String staticPath = FreeMarkerUtils.process(template.getStaticPath(), model);
		Integer count = staticService.build(template.getTemplatePath(), staticPath, model);


		return count+"";
	}

}