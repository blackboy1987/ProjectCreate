
package com.igomall.service;

import java.util.List;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.Module;
import com.igomall.entity.Property;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface PropertyService extends BaseService<Property, Long> {

	boolean nameExists(String name);

	Page<Property> findPage(Pageable pageable, Module module);

	List<Property> findList(Module module);

}