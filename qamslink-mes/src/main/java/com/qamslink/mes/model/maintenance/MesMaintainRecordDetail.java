package com.qamslink.mes.model.maintenance;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mes_maintain_record_detail")
@Erupt(name = "保养记录详情")
public class MesMaintainRecordDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "保养项", column = "name"),
            edit = @Edit(title = "保养项", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMaintenanceItem maintenanceItem;

    @EruptField(
            views = @View(title = "保养状态"),
            edit = @Edit(title = "保养状态",
                    boolType = @BoolType(trueText = "已保养", falseText = "未保养"))
    )
    private Boolean checkStatus;

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
