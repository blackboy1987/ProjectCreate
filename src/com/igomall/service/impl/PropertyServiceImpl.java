
package com.igomall.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.dao.PropertyDao;
import com.igomall.entity.Module;
import com.igomall.entity.Property;
import com.igomall.service.PropertyService;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class PropertyServiceImpl extends BaseServiceImpl<Property, Long> implements PropertyService {

	@Autowired
	private PropertyDao propertyDao;
	
	@Override
	public boolean nameExists(String name) {
		return propertyDao.exists("name", StringUtils.lowerCase(name));
	}

	@Override
	public Page<Property> findPage(Pageable pageable, Module module) {
		return propertyDao.findPage(pageable,module);
	}

	@Override
	public List<Property> findList(Module module) {
		return propertyDao.findList(module);
	}
	
}