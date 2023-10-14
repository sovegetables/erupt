package com.qamslink.mes.type;

import com.qamslink.mes.core.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;

public enum IncomingRecordStatus implements IEnum<Integer> {
    PENDING_COMMIT(IncomingRecordStatus.PENDING_COMMIT_V, "未处理"),
    REWORK(IncomingRecordStatus.REWORK_V, "已返工"),
    SCRAPPED(IncomingRecordStatus.SCRAPPED_V, "已报废"),
    REJECTION(IncomingRecordStatus.REJECTION_V, "已拒收");

    public static final int PENDING_COMMIT_V = 0;
    public static final int REWORK_V = 1;
    public static final int SCRAPPED_V = 2;
    public static final int REJECTION_V = 3;

    private final int value;
    private final String label;

    IncomingRecordStatus(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(IncomingRecordStatus.values());
        }
    }
}
