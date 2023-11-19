package xyz.erupt.upms.converter;

import com.google.gson.JsonDeserializer;
import xyz.erupt.core.query.IEnum;

import javax.persistence.AttributeConverter;

public abstract class AbEnumConverter<T extends IEnum<Integer>> implements AttributeConverter<T, Integer>, JsonDeserializer<T> {
    @Override
    public Integer convertToDatabaseColumn(T t) {
        return t == null? convertNullToColumn(): t.getValue();
    }

    protected abstract Integer convertNullToColumn();
}
