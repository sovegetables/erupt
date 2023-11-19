package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.BarCodeType;
import xyz.erupt.upms.converter.AbEnumConverter;

import java.lang.reflect.Type;
import java.util.Objects;

public class BarCodeTypeConverter extends AbEnumConverter<BarCodeType> {
    @Override
    public BarCodeType convertToEntityAttribute(Integer value) {
        BarCodeType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown BarCodeType text : " + value);
    }

    private static BarCodeType convertToEntity(Integer value) {
        for (BarCodeType s : BarCodeType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return BarCodeType.NOT.getValue();
    }

    @Override
    public BarCodeType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
