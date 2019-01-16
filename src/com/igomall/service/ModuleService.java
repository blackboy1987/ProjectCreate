
package com.igomall.service;

import com.igomall.entity.Module;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface ModuleService extends BaseService<Module, Long> {

	boolean nameExists(String name);

}