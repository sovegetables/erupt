package com.qamslink.mes.type;

import xyz.erupt.core.query.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;

public enum InspectionStatus implements IEnum<Integer> {

    FINISHED(InspectionStatus.STATUS_FINISHED, "已完成"),
    UNTREATED(InspectionStatus.STATUS_UNTREATED, "未处理"),
    SCRAPPED(InspectionStatus.STATUS_SCRAPPED, "已报废"),
    REWORKED(InspectionStatus.STATUS_REWORKED, "已返工");

    public static final int STATUS_FINISHED = 0;
    public static final int STATUS_UNTREATED = 1;
    public static final int STATUS_SCRAPPED = 2;
    public static final int STATUS_REWORKED = 3;
    private final int value;
    private final String label;


    InspectionStatus(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(InspectionStatus.values());
        }
    }
}
