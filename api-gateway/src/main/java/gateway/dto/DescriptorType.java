/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway.dto;

import java.util.Date;

public class DescriptorType {

    private Long id;
    private Class paramClass;

    public DescriptorType() {
    }

    public DescriptorType(Class paramClass) {
        this.paramClass = paramClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class paramClass) {
        this.paramClass = paramClass;
    }

    public String getStringMessageByParamClass() {
        if (Integer.class.equals(paramClass)) {
            return "integer";
        } else if (Double.class.equals(paramClass)) {
            return "decimal number";
        } else if (Date.class.equals(paramClass)) {
            return "date";
        }
        return "N/A";
    }

}
