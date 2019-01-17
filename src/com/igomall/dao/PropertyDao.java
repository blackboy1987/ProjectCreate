
package com.igomall.dao;

import java.util.List;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.Module;
import com.igomall.entity.Property;

/**
 * Dao - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface PropertyDao extends BaseDao<Property, Long> {

	Page<Property> findPage(Pageable pageable, Module module);

	List<Property> findList(Module module);

}