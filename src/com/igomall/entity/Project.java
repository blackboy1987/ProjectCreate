
package com.igomall.entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 项目。数据库里面的数据库名
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Project extends BaseEntity<Long> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3851726601079863533L;

	/**
	 * 项目名称
	 */
	@NotEmpty
	@Column(nullable = false)
	@JsonView({ListView.class,EditView.class})
	private String name;

	@JsonView({ListView.class,EditView.class})
	private String memo;

	@OneToMany(mappedBy = "project",fetch = FetchType.LAZY)
	private Set<Module> modules = new HashSet<>();
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	
	
	public interface ListView extends BaseView{

	}
	public interface EditView extends BaseView{

	}
}