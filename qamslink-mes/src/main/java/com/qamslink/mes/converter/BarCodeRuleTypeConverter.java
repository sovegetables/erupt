package com.qamslink.mes.converter;

import com.qamslink.mes.type.BarCodeRuleType;

import java.util.Objects;

public class BarCodeRuleTypeConverter extends AbEnumConverter<BarCodeRuleType> {
    @Override
    public BarCodeRuleType convertToEntityAttribute(Integer value) {
        for (BarCodeRuleType s : BarCodeRuleType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown BarCodeRuleType text : " + value);
    }
}
