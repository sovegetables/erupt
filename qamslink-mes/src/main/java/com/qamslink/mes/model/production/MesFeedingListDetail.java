package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.warehouse.MesWarehouse;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mes_feeding_list_detail")
@Setter
@Getter
@Erupt(name = "上料清单明细")
public class MesFeedingListDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "物料", column = "name"),
            edit = @Edit(title = "物料", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "实际用量"),
            edit = @Edit(title = "实际用量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal actualNum;

    @EruptField(
            views = @View(title = "发料方式"),
            edit = @Edit(title = "发料方式", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "直接领料", value = "1"),
                            @VL(label = "直接倒冲", value = "2")
                    }))
    )
    private Integer issueType = 2;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "预扣仓", column = "name")
            },
            edit = @Edit(title = "预扣仓", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "name"))

    )
    private MesWarehouse warehouse;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

}
