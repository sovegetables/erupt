package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.InspectionStatus;
import xyz.erupt.upms.converter.AbEnumConverter;

import java.lang.reflect.Type;
import java.util.Objects;

public class InspectionStatusConverter extends AbEnumConverter<InspectionStatus> {
    @Override
    public InspectionStatus convertToEntityAttribute(Integer value) {
        InspectionStatus s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown InpesctionType text : " + value);
    }

    private static InspectionStatus convertToEntity(Integer value) {
        for (InspectionStatus s : InspectionStatus.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return InspectionStatus.STATUS_FINISHED;
    }

    @Override
    public InspectionStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
