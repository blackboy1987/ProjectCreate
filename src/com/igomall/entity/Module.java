
package com.igomall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - 项目里面的模块，对应与数据库里面的表
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Module extends BaseEntity<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6316066453319486255L;

	/**
     * 模块名称
     */
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "module",fetch = FetchType.LAZY)
    private Set<Property> properties = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}