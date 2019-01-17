
package com.igomall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity - 项目里面的模块，对应与数据库里面的表
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Module extends BaseEntity<Long> {

	private static final long serialVersionUID = -6316066453319486255L;

	/**
     * 模块名称
     */
    @JsonView({ListView.class,EditView.class})
    private String name;

    @JsonView({ListView.class,EditView.class})
    private String memo;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable=false)
    private Project project;

    @OneToMany(mappedBy = "module",fetch = FetchType.LAZY)
    private Set<Property> properties = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
    
    @Transient
    @JsonView({ListView.class,EditView.class})
    public Long getProjectId() {
    	if(project!=null) {
    		return project.getId();
    	}
    	return null;
    }
    
    @Transient
    @JsonView({ListView.class})
    public String getProjectName() {
    	if(project!=null) {
    		return project.getName();
    	}
    	return null;
    }
    
    /**
     * 	首字母大写
     * @return
     */
    @Transient
    public String capitalize() {
    	return StringUtils.capitalize(name);
    }
    
    public interface ListView extends BaseView {};
    
    public interface EditView extends BaseView{}
}