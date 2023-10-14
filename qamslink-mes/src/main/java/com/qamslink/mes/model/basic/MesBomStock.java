package com.qamslink.mes.model.basic;

import com.qamslink.mes.model.warehouse.MesWarehouse;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mes_bom_stock")
@Getter
@Setter
@Erupt(name = "bom详情"
//        , dataProxy = MesBomStockService.class
)
public class MesBomStock extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "物料分类", column = "stockCategory.name"),
                    @View(title = "单位", column = "unit")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true),type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "用量", show = false),
            edit = @Edit(title = "用量", notNull = false, numberType = @NumberType(min = 0), show = false)
    )
    private BigDecimal num;

    @EruptField(
            views = @View(title = "需用数量"),
            edit = @Edit(title = "需用数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal needNum;

    @EruptField(
            views = @View(title = "生产数量"),
            edit = @Edit(title = "生产数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal parentNum;

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
            edit = @Edit(title = "预扣仓", notNull = false, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType())
    )
    private MesWarehouse warehouse;

//    @Transient
//    @OneToMany
//    @EruptField(
//            views = @View(title = "工序列表"),
//            edit = @Edit(
//                    title = "工序列表",
//                    type = EditType.TAB_TABLE_REFER,
//                    readonly = @Readonly
//            ),
//            extra = @Extra(clazz = MesBomWorkingProcedureVO.class, idClass = BaseModel.class,params = {"stock.id"},
//                    sql = "select wp.id, wp.name, bwp.sort from mes_bom_working_procedure bwp left join mes_bom b on b.id = bwp.bom_id " +
//                            "left join mes_working_procedure wp on wp.id = bwp.working_procedure_id where b.main_stock_id = #{0}")
//    )
//    private List<MesBomWorkingProcedureVO> bomWorkingProcedures;

}
