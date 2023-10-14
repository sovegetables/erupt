package com.qamslink.mes.converter;

import com.qamslink.mes.type.IncomingRecordStatus;

import java.util.Objects;

public class IncomingRecordStatusConverter extends AbEnumConverter<IncomingRecordStatus> {
    @Override
    public IncomingRecordStatus convertToEntityAttribute(Integer value) {
        for (IncomingRecordStatus s : IncomingRecordStatus.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return s;
            }
        }
        throw new RuntimeException("Unknown TicketType text : " + value);
    }
}
