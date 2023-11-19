package xyz.erupt.upms.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import xyz.erupt.upms.type.UserType;

import java.lang.reflect.Type;
import java.util.Objects;

public class UserTypeConverter extends AbEnumConverter<UserType> {

    private static UserType convertToEntity(Integer value) {
        for (UserType s : UserType.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected Integer convertNullToColumn() {
        return UserType.NORMAL.getValue();
    }

    @Override
    public UserType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int value = json.getAsJsonPrimitive().getAsInt();
        return convertToEntity(value);
    }

    @Override
    public UserType convertToEntityAttribute(Integer value) {
        UserType s = convertToEntity(value);
        if (s != null) return s;
        throw new RuntimeException("Unknown TicketType text : " + value);
    }
}
