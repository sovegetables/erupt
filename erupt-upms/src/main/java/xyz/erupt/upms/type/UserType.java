package xyz.erupt.upms.type;

import xyz.erupt.core.query.IEnum;

public enum UserType implements IEnum<Integer> {
    NORMAL(0, "普通"),
    SUPPLIER(1, "供应商");
    private final int value;
    private final String label;

    UserType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getValueStr() {
        return String.valueOf(value);
    }

}
