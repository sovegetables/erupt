package com.qamslink.mes.type;

import xyz.erupt.core.query.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;

public enum ScrapOrderType implements IEnum<Integer> {
    INSPECTION(ScrapOrderType.INSPECTION_VALUE, "巡检报废"),
    PRODUCTION(ScrapOrderType.PRODUCTION_VAULE, "生产报废");

    public static final int INSPECTION_VALUE = 1;
    public static final int PRODUCTION_VAULE = 2;
    private final int value;
    private final String label;

    ScrapOrderType(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(ScrapOrderType.values());
        }
    }
}
