
package com.igomall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity - 模块里面的属性。对应与数据库里面的表中的字段
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Entity
public class Property extends BaseEntity<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3688896589685565623L;

	/**
     * 名称
     */
	@JsonView({ListView.class,EditView.class})
    private String name;

    /**
     * 类型
     * 0：Integer
     * 1：Long
     * 2：Double
     * 3：Float
     * 4：BigDecimal
     * 5：Boolean
     * 6：Date
     * 7：String
     * 9：
     * 10：
     * 11：
     */
	@JsonView({ListView.class,EditView.class})
    private String type;

	@JsonView({ListView.class,EditView.class})
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    @Transient
	@JsonView({ListView.class})
    public String getModuleName() {
    	if(module!=null) {
    		return module.getName();
    	}
    	return null;
    }
    
    @Transient
	@JsonView({ListView.class,EditView.class})
    public Long getModuleId() {
    	if(module!=null) {
    		return module.getId();
    	}
    	return null;
    }
    
    @Transient
	@JsonView({ListView.class})
    public String getProjectName() {
    	if(module!=null) {
    		return module.getProjectName();
    	}
    	return null;
    }
    
    @Transient
	@JsonView({ListView.class})
    public Long getProjectId() {
    	if(module!=null) {
    		return module.getProjectId();
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