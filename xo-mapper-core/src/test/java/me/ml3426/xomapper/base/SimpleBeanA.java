package me.ml3426.xomapper.base;

import java.beans.Transient;
import java.util.Date;

/**
 * 简单BeanA
 * 
 * @author ml3426 
 * @since 0.0.1
 */
public class SimpleBeanA {
    
    private String property0;
    
    private Date property1;
    
    private transient int property2;
    
    private boolean property3;
    
    private Boolean property4;
    
    private BaseEnum property5;
    
    private String property6;

    private String property7;

    public String getProperty0() {
        return property0;
    }

    public void setProperty0(String property0) {
        this.property0 = property0;
    }

    public Date getProperty1() {
        return property1;
    }

    public void setProperty1(Date property1) {
        this.property1 = property1;
    }

    public int getProperty2() {
        return property2;
    }

    public void setProperty2(int property2) {
        this.property2 = property2;
    }

    public boolean isProperty3() {
        return property3;
    }

    public void setProperty3(boolean property3) {
        this.property3 = property3;
    }

    public Boolean getProperty4() {
        return property4;
    }

    public void setProperty4(Boolean property4) {
        this.property4 = property4;
    }

    public BaseEnum getProperty5() {
        return property5;
    }

    public void setProperty5(BaseEnum property5) {
        this.property5 = property5;
    }

    public String getProperty6() {
        return property6;
    }

    public SimpleBeanA setProperty6(String property6) {
        this.property6 = property6;
        return this;
    }

    public String getProperty8() {
        return property7;
    }

    public void setProperty8(String property7) {
        this.property7 = property7;
    }
}
