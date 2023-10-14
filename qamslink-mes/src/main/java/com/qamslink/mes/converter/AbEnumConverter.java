package com.qamslink.mes.converter;

import com.qamslink.mes.core.IEnum;

import javax.persistence.AttributeConverter;

public abstract class AbEnumConverter<T extends IEnum<Integer>> implements AttributeConverter<T, Integer> {
    @Override
    public Integer convertToDatabaseColumn(T t) {
        return t.getValue();
    }
}
