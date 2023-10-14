package com.qamslink.mes.converter;

import com.qamslink.mes.type.InspectionType;

import java.util.Objects;

public class InspectionTypeConverter extends AbEnumConverter<InspectionType> {
    @Override
    public InspectionType convertToEntityAttribute(Integer value) {
        for (InspectionType s : InspectionType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown InpesctionType text : " + value);
    }
}
