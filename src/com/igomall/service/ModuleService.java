
package com.igomall.service;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.Module;
import com.igomall.entity.Project;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ModuleService extends BaseService<Module, Long> {

	boolean nameExists(String name);

	Page<Module> findPage(Pageable pageable, Project project);

}