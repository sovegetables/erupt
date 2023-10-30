package com.qamslink.mes.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qamslink.mes.type.BarCodeRuleType;

import java.lang.reflect.Type;
import java.util.Objects;

public class BarCodeRuleTypeConverter extends AbEnumConverter<BarCodeRuleType> {
    @Override
    public BarCodeRuleType convertToEntityAttribute(Integer value) {
        BarCodeRuleType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown BarCodeRuleType text : " + value);
    }

    private static BarCodeRuleType convertToEntity(Integer value) {
        for (BarCodeRuleType s : BarCodeRuleType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return BarCodeRuleType.CATEGORY_TYPE_BAR_VALUE;
    }

    @Override
    public BarCodeRuleType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }
}
