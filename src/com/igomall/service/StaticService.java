
package com.igomall.service;

import java.util.Map;

public interface StaticService {

	int build(String templatePath, String staticPath, Map<String, Object> model);
	
	String build1(String templatePath, String staticPath, Map<String, Object> model);

	int build(String templatePath, String staticPath);

	int buildOther();

	int buildAll();

	int delete(String staticPath);

	int deleteOther();

}