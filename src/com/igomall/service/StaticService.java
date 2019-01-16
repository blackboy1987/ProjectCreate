
package com.igomall.service;

import java.util.Map;

public interface StaticService {

	int build(String templatePath, String staticPath, Map<String, Object> model);

	int build(String templatePath, String staticPath);

	int buildIndex();

	int buildSitemap();

	int buildOther();

	int buildAll();

	int delete(String staticPath);

	int deleteIndex();

	int deleteOther();

}