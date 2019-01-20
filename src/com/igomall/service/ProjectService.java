
package com.igomall.service;

import com.igomall.entity.Project;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ProjectService extends BaseService<Project, Long> {

	boolean nameExists(String name);

	/**
	 * 判断项目名称是否唯一
	 *
	 * @param id
	 *            ID
	 * @param name
	 *           项目名称
	 * @return 项目名称是否唯一
	 */
	boolean nameUnique(Long id, String name);
}