package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.ScrapOrderType;
import xyz.erupt.upms.converter.AbEnumConverter;

import java.lang.reflect.Type;
import java.util.Objects;

public class ScrapOrderTypeConverter extends AbEnumConverter<ScrapOrderType> {
    @Override
    public ScrapOrderType convertToEntityAttribute(Integer value) {
        ScrapOrderType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown ScrapOrderType text : " + value);
    }

    private static ScrapOrderType convertToEntity(Integer value) {
        for (ScrapOrderType s : ScrapOrderType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return ScrapOrderType.INSPECTION_VALUE;
    }

    @Override
    public ScrapOrderType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
