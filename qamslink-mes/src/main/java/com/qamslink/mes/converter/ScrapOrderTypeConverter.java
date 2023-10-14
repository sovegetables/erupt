package com.qamslink.mes.converter;

import com.qamslink.mes.type.ScrapOrderType;
import com.qamslink.mes.type.TicketType;

import java.util.Objects;

public class ScrapOrderTypeConverter extends AbEnumConverter<ScrapOrderType> {
    @Override
    public ScrapOrderType convertToEntityAttribute(Integer value) {
        for (ScrapOrderType s : ScrapOrderType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown ScrapOrderType text : " + value);
    }
}
