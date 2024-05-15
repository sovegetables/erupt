package com.qamslink.mes.model.basic;

import com.qamslink.mes.core.SerialNumModel;
import com.qamslink.mes.model.warehouse.MesWarehouse;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mes_bom_stock")
@Getter
@Setter
@Erupt(name = "物料清单明细")
public class MesBomStock extends SerialNumModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code"),
                    @View(title = "物料名称", column = "name"),
                    @View(title = "规格型号", column = "spec")
            },
            edit = @Edit(title = "物料编码", notNull = true,
                    search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"),
                    type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {@View(title = "单位", column = "name")},
            edit = @Edit(title = "单位", notNull = true,
                    recommendBy = @EditRecommend(dependModel = "stock", dependModelPKey = "unit_id", dependField = "unit_name"),
                    type = EditType.REFERENCE_TABLE)
    )
    private Unit unit;

    @EruptField(
            views = @View(title = "用量", show = false),
            edit = @Edit(title = "用量", numberType = @NumberType(min = 0, step = 0.0001), show = false)
    )
    private BigDecimal num;

    @EruptField(
            views = @View(title = "需用数量"),
            edit = @Edit(title = "需用数量", notNull = true, numberType = @NumberType(min = 0, step = 0.0001))
    )
    private BigDecimal needNum;

    @EruptField(
            views = @View(title = "生产数量"),
            edit = @Edit(title = "生产数量", notNull = true, numberType = @NumberType(min = 0, step = 0.0001))
    )
    private BigDecimal parentNum;

    @EruptField(
            views = @View(title = "发料方式", width = "100px"),
            edit = @Edit(title = "发料方式", type = EditType.CHOICE,
                    notNull = true,
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
            edit = @Edit(title = "预扣仓",
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType())
    )
    private MesWarehouse warehouse;
}
