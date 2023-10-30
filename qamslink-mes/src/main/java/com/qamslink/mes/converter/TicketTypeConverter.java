package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.BarCodeType;
import com.qamslink.mes.type.TicketType;

import java.lang.reflect.Type;
import java.util.Objects;

public class TicketTypeConverter extends AbEnumConverter<TicketType> {
    @Override
    public TicketType convertToEntityAttribute(Integer value) {
        TicketType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown TicketType text : " + value);
    }

    private static TicketType convertToEntity(Integer value) {
        for (TicketType s : TicketType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return TicketType.NOT.getValue();
    }

    @Override
    public TicketType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
