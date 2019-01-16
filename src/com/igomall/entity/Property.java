
package com.igomall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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
    private String name;

    /**
     * 类型
     * 0：Integer
     * 2：Long
     * 3：Double
     * 4：Float
     * 5：BigDecimal
     * 6：Boolean
     * 7：Date
     * 8：String
     * 9：
     * 10：
     * 11：
     */
    private Integer type;

    private Boolean isNull;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getNull() {
        return isNull;
    }

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}