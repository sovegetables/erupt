package com.qamslink.mes.type;

import xyz.erupt.core.query.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;

public enum BarCodeRuleType implements IEnum<Integer> {

    CATEGORY_TYPE_BAR(BarCodeRuleType.CATEGORY_TYPE_BAR_VALUE, "条码类型"),
    CATEGORY_TYPE_ORDER(BarCodeRuleType.CATEGORY_TYPE_ORDER_VALUE, "单据类型");

    public static final int CATEGORY_TYPE_BAR_VALUE = 0;
    public static final int CATEGORY_TYPE_ORDER_VALUE = 1;
    private final int value;
    private final String label;


    BarCodeRuleType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getValueStr() {
        return String.valueOf(value);
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Component
    public static class Handler implements ChoiceFetchHandler{
        @Override
        public List<VLModel> fetch(String[] params) {
            return IEnum.HandlerHelper.createFetchHandlers(BarCodeRuleType.values());
        }
    }
}
