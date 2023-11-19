package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.IncomingRecordStatus;
import xyz.erupt.upms.converter.AbEnumConverter;

import java.lang.reflect.Type;
import java.util.Objects;

public class IncomingRecordStatusConverter extends AbEnumConverter<IncomingRecordStatus> {
    @Override
    public IncomingRecordStatus convertToEntityAttribute(Integer value) {
        IncomingRecordStatus s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown TicketType text : " + value);
    }

    private static IncomingRecordStatus convertToEntity(Integer value) {
        for (IncomingRecordStatus s : IncomingRecordStatus.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return IncomingRecordStatus.PENDING_COMMIT_V;
    }

    @Override
    public IncomingRecordStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
