package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.InspectionType;
import xyz.erupt.upms.converter.AbEnumConverter;

import java.lang.reflect.Type;
import java.util.Objects;

public class InspectionTypeConverter extends AbEnumConverter<InspectionType> {
    @Override
    public InspectionType convertToEntityAttribute(Integer value) {
        InspectionType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown InpesctionType text : " + value);
    }

    private static InspectionType convertToEntity(Integer value) {
        for (InspectionType s : InspectionType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return InspectionType.NOT.getValue();
    }

    @Override
    public InspectionType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
