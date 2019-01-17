
package com.igomall.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.dao.ModuleDao;
import com.igomall.entity.Module;
import com.igomall.entity.Project;
import com.igomall.service.ModuleService;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl<Module, Long> implements ModuleService {

	@Autowired
	private ModuleDao moduleDao;
	
	@Override
	public boolean nameExists(String name) {
		return moduleDao.exists("name", StringUtils.lowerCase(name));
	}

	@Override
	public Page<Module> findPage(Pageable pageable, Project project) {
		return moduleDao.findPage(pageable,project);
	}
	
}