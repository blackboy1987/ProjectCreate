
package com.igomall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.dao.ModuleDao;
import com.igomall.entity.Module;
import com.igomall.entity.Project;

/**
 * Dao - 角色
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Repository
public class ModuleDaoImpl extends BaseDaoImpl<Module, Long> implements ModuleDao {

	@Override
	public Page<Module> findPage(Pageable pageable, Project project) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Module> criteriaQuery = criteriaBuilder.createQuery(Module.class);
		Root<Module> root = criteriaQuery.from(Module.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(project!=null) {
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("project"),project));
		}
		criteriaQuery.where(restrictions);
		
		return findPage(criteriaQuery, pageable);
		
	}

}