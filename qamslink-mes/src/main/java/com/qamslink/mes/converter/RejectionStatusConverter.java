package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.RejectionSlipStatus;
import com.qamslink.mes.type.ScrapOrderType;

import java.lang.reflect.Type;
import java.util.Objects;

public class RejectionStatusConverter extends AbEnumConverter<RejectionSlipStatus> {
    @Override
    public RejectionSlipStatus convertToEntityAttribute(Integer value) {
        RejectionSlipStatus s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown RejectionSlipStatus text : " + value);
    }

    private static RejectionSlipStatus convertToEntity(Integer value) {
        for (RejectionSlipStatus s : RejectionSlipStatus.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return RejectionSlipStatus.PENDING_COMMIT_V;
    }

    @Override
    public RejectionSlipStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
