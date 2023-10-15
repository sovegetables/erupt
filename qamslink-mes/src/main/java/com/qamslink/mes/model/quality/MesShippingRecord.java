package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_inspection_record")
@Erupt(name = "出货检验记录",
//        dataProxy = MesShippingService.class,
        orderBy = "MesShippingRecord.createTime desc",
        filter = @Filter(value = "MesShippingRecord.tenantId",
                params = {"and MesShippingRecord.deleted = false", "and MesShippingRecord.type = 3"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_inspection_record set deleted = true where id = ?")
public class MesShippingRecord extends HyperModelVo {

    @EruptField(
            views = @View(title = "检验单号"),
            edit = @Edit(title = "检验单号", notNull = true, search = @Search(vague = true))
    )
    private String sn;

    private Integer type;

    @EruptField(
            views = @View(title = "检验数量"),
            edit = @Edit(title = "检验数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal inspectionNum;

    @EruptField(
            views = @View(title = "毁坏数量"),
            edit = @Edit(title = "毁坏数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal destroyCount;

    @EruptField(
            views = @View(title = "检验结果"),
            edit = @Edit(title = "检验结果", notNull = true, search = @Search(vague = true), boolType = @BoolType(trueText = "合格", falseText = "不合格"))
    )
    private Boolean result;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未处理", value = "0"),
                            @VL(label = "已返工", value = "1"),
                            @VL(label = "已报废", value = "2"),
                    }
            ))
    )
    private Integer status = 0;

    @EruptField(
            views = @View(title = "合格数"),
            edit = @Edit(title = "合格数", numberType = @NumberType(min = 0))
    )
    private BigDecimal qualifiedCount;

    @EruptField(
            views = @View(title = "不合格数"),
            edit = @Edit(title = "不合格数", numberType = @NumberType(min = 0))
    )
    private BigDecimal unqualifiedCount;

    @JoinColumn(name = "inspection_record_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesInspectionRecordDetail> inspectionRecordDetails;
    private Boolean deleted = false;
}
