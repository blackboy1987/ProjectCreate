
package com.igomall.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.dao.PropertyDao;
import com.igomall.entity.Module;
import com.igomall.entity.Property;

/**
 * Dao - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class PropertyDaoImpl extends BaseDaoImpl<Property, Long> implements PropertyDao {

	@Override
	public Page<Property> findPage(Pageable pageable, Module module) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
		Root<Property> root = criteriaQuery.from(Property.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(module!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("module"),module));
		}
		criteriaQuery.where(restrictions);
		
		return findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Property> findList(Module module) {
		if(module==null) {
			return Collections.emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
		Root<Property> root = criteriaQuery.from(Property.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("module"),module));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdDate")));
		return findList(criteriaQuery);
	}

}