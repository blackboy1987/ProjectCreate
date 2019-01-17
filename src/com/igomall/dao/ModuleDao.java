/*



 */
package com.igomall.dao;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.Module;
import com.igomall.entity.Project;

/**
 * Dao - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ModuleDao extends BaseDao<Module, Long> {

	Page<Module> findPage(Pageable pageable, Project project);

}