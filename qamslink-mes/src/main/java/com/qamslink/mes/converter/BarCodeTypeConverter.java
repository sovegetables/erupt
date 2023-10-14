package com.qamslink.mes.converter;

import com.qamslink.mes.type.BarCodeType;

import java.util.Objects;

public class BarCodeTypeConverter extends AbEnumConverter<BarCodeType> {
    @Override
    public BarCodeType convertToEntityAttribute(Integer value) {
        for (BarCodeType s : BarCodeType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown BarCodeType text : " + value);
    }
}
