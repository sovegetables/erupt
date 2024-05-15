package com.qamslink.mes.core;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.PreDataProxy;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@PreDataProxy(SerialNumDataProxy.class)
@MappedSuperclass
public class SerialNumModel extends BaseModel {

    @EruptField(
            views = {@View(title = "行号", type = ViewType.SERIAL_NUMBER)},
            sort = 1,
            edit = @Edit(title = "行号",
                    notNull = true,
                    readonly = @Readonly,
                    type = EditType.SERIAL_NUMBER,
                    inputType = @InputType
            )
    )
    private Integer serialNum;
}
