package com.qamslink.mes.type;

import com.qamslink.mes.core.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import java.util.List;

public enum RejectionSlipStatus implements IEnum<Integer> {
    PENDING_COMMIT(RejectionSlipStatus.PENDING_COMMIT_V, "待处理"),
    AUDITING(RejectionSlipStatus.AUDITING_V, "待审核"),
    AUDITED(RejectionSlipStatus.AUDITED_V, "已审核");
    public static final int PENDING_COMMIT_V = 1;
    public static final int AUDITING_V = 2;
    public static final int AUDITED_V = 3;

    private final int value;
    private final String label;


    RejectionSlipStatus(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(RejectionSlipStatus.values());
        }
    }
}
