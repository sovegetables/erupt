package com.qamslink.mes.type;

import xyz.erupt.core.query.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.stream.Collectors;

public enum InspectionType implements IEnum<Integer> {

    NOT(InspectionType.NOT_VALUE, ""),
    RANDOM(InspectionType.RANDOM_VALUE, "抽检"),
    ALL(InspectionType.ALL_VALUE, "全检");

    public static final int NOT_VALUE = 0;
    public static final int RANDOM_VALUE = 1;
    public static final int ALL_VALUE = 2;
    private final int value;
    private final String label;


    InspectionType(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(InspectionType.values())
                    .stream().filter(i->!i.getValue().equals(NOT.getValueStr()))
                    .collect(Collectors.toList());
        }
    }
}
