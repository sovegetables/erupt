package com.qamslink.mes.converter;

import com.qamslink.mes.type.LabelRuleType;
import com.qamslink.mes.type.LabelType;

import java.util.Objects;

public class LabelRuleTypeConverter extends AbEnumConverter<LabelRuleType> {
    @Override
    public LabelRuleType convertToEntityAttribute(Integer value) {
        for (LabelRuleType s : LabelRuleType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown TicketType text : " + value);
    }
}
