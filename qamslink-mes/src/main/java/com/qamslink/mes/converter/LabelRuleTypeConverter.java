package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.LabelRuleType;
import xyz.erupt.upms.converter.AbEnumConverter;

import java.lang.reflect.Type;
import java.util.Objects;

public class LabelRuleTypeConverter extends AbEnumConverter<LabelRuleType> {
    @Override
    public LabelRuleType convertToEntityAttribute(Integer value) {
        LabelRuleType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown TicketType text : " + value);
    }

    private static LabelRuleType convertToEntity(Integer value) {
        for (LabelRuleType s : LabelRuleType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return LabelRuleType.TYPE_NOT;
    }

    @Override
    public LabelRuleType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
