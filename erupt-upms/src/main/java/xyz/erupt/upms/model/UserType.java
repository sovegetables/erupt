package xyz.erupt.upms.model;

import org.apache.poi.ss.formula.functions.T;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public enum UserType {
    NORMAL(0, "普通"),
    SUPPLIER(1, "供应商");
    private final int value;
    private final String label;

    UserType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getValueStr() {
        return String.valueOf(value);
    }

    public static class Converter implements AttributeConverter<UserType, Integer>{

        @Override
        public Integer convertToDatabaseColumn(UserType userType) {
            return userType.getValue();
        }

        @Override
        public UserType convertToEntityAttribute(Integer value) {
            for (UserType s : UserType.values()) {
                if (Objects.equals(s.getValue(), value)) {
                    return s;
                }
            }
            throw new RuntimeException("Unknown TicketType text : " + value);
        }
    }
}
