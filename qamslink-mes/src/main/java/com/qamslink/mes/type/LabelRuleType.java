package com.qamslink.mes.type;

import xyz.erupt.core.query.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum LabelRuleType implements IEnum<Integer> {
    NOT(LabelRuleType.TYPE_NOT, ""),
    MATERIAL_CODE(LabelRuleType.TYPE_MATERIAL_CODE, "物料条码"),
    MATERIAL_CATEGORY(LabelRuleType.TYPE_MATERIAL_CATEGORY, "物料分类"),
    MATERIAL_ALL(LabelRuleType.TYPE_MATERIAL_ALL, "所有物料");
    private final int value;
    private final String label;

    public static final int TYPE_NOT = 0;
    public static final int TYPE_MATERIAL_CODE = 1;
    public static final int TYPE_MATERIAL_CATEGORY = 2;
    public static final int TYPE_MATERIAL_ALL = 3;

    LabelRuleType(int value, String label) {
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
            return HandlerHelper.createFetchHandlers(LabelRuleType.values())
                    .stream().filter(i-> !Objects.equals(i.getValue(), NOT.getValueStr()))
                    .collect(Collectors.toList());
        }
    }
}
