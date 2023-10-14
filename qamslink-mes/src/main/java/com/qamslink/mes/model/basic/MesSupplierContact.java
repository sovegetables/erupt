package com.qamslink.mes.model.basic;

import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="mes_supplier_contact")
@Data
@Erupt(name = "供应商联系方式")
public class MesSupplierContact extends BaseModel {

    @EruptField(
            views = @View(
                    title = "联系人", sortable = true
            ),
            edit = @Edit(
                    title = "联系人",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String personName;

    @EruptField(
            views = @View(
                    title = "联系电话", sortable = true
            ),
            edit = @Edit(
                    title = "联系电话",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String phone;

    @EruptField(
            views = @View(
                    title = "备注"
            ),
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;
}
