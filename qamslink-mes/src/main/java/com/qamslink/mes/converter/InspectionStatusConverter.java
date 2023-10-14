package com.qamslink.mes.converter;

import com.qamslink.mes.type.InspectionStatus;
import com.qamslink.mes.type.InspectionType;

import java.util.Objects;

public class InspectionStatusConverter extends AbEnumConverter<InspectionStatus> {
    @Override
    public InspectionStatus convertToEntityAttribute(Integer value) {
        for (InspectionStatus s : InspectionStatus.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown InpesctionType text : " + value);
    }
}
