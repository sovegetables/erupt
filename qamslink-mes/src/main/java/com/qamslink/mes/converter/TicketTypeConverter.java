package com.qamslink.mes.converter;

import com.qamslink.mes.type.BarCodeType;
import com.qamslink.mes.type.TicketType;

import java.util.Objects;

public class TicketTypeConverter extends AbEnumConverter<TicketType> {
    @Override
    public TicketType convertToEntityAttribute(Integer value) {
        for (TicketType s : TicketType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown TicketType text : " + value);
    }
}
