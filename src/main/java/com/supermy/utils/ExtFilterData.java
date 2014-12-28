package com.supermy.utils;

/**
 * Created by jamesmo on 2014/6/12.
 */
/**
 * @author Administrator
 *
 */
public class ExtFilterData
{
    private String type;
    private String value;
    private String comparison;
    private String field;
    private String property;

    public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}