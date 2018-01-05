package me.ml3426.xomapper.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

public enum BaseEnum {
    A("a", "b"), B("b", "a");
    
    private final String a;
    
    private final String b;

    BaseEnum(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("a", a)
                .append("b", b)
                .toString();
    }
}
