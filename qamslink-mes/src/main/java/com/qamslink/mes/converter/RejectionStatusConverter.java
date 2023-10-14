package com.qamslink.mes.converter;

import com.qamslink.mes.type.RejectionSlipStatus;
import com.qamslink.mes.type.ScrapOrderType;

import java.util.Objects;

public class RejectionStatusConverter extends AbEnumConverter<RejectionSlipStatus> {
    @Override
    public RejectionSlipStatus convertToEntityAttribute(Integer value) {
        for (RejectionSlipStatus s : RejectionSlipStatus.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown RejectionSlipStatus text : " + value);
    }
}
