package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.LabelType;

import java.lang.reflect.Type;
import java.util.Objects;

public class LabelTypeConverter extends AbEnumConverter<LabelType> {
    @Override
    public LabelType convertToEntityAttribute(Integer value) {
        LabelType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown TicketType text : " + value);
    }

    private static LabelType convertToEntity(Integer value) {
        for (LabelType s : LabelType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return LabelType.NOT.getValue();
    }

    @Override
    public LabelType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
