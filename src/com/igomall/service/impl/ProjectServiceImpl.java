
package com.igomall.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igomall.dao.ProjectDao;
import com.igomall.entity.Project;
import com.igomall.service.ProjectService;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, Long> implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public boolean nameExists(String name) {
		return projectDao.exists("name", StringUtils.lowerCase(name));
	}

	@Override
	public boolean nameUnique(Long id, String name) {
		return projectDao.unique(id, "name", StringUtils.lowerCase(name));
	}
}