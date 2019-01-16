
package com.igomall.service;

import java.util.List;

import com.igomall.Template;

public interface TemplateService {

	List<Template> getAll();

	Template get(String id);

	String read(String id);

	String read(Template template);

	void write(String id, String content);

	void write(Template template, String content);

}