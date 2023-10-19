package com.qamslink.mes.converter;

import com.qamslink.mes.type.LabelType;
import com.qamslink.mes.type.TicketType;

import java.util.Objects;

public class LabelTypeConverter extends AbEnumConverter<LabelType> {
    @Override
    public LabelType convertToEntityAttribute(Integer value) {
        for (LabelType s : LabelType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown TicketType text : " + value);
    }
}
