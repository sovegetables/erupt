package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "mes_production_inspection_record_detail")
@Erupt(name = "生产检验记录详情")
public class MesProductionInspectionRecordDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "检验项", column = "name"),
            edit = @Edit(title = "检验项", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesInspectionItem inspectionItem;

    @EruptField(
            views = @View(title = "结果"),
            edit = @Edit(title = "结果", boolType = @BoolType(
                    trueText = "合格", falseText = "不合格"
            ), notNull = true, search = @Search)
    )
    private Boolean result;

    @EruptField(
            views = @View(title = "不合格数量"),
            edit = @Edit(title = "不合格数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;
}
