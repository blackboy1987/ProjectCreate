
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

}