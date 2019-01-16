
package com.igomall.service;

import com.igomall.entity.Property;

/**
 * Service - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public interface PropertyService extends BaseService<Property, Long> {

	boolean nameExists(String name);

}