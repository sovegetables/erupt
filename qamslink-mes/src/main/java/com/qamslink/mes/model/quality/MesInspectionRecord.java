package com.qamslink.mes.model.quality;

import com.qamslink.mes.model.basic.MesResource;
import com.qamslink.mes.model.equipment.MesEquipment;
import com.qamslink.mes.model.production.MesProductionScheduleDetail;
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
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_inspection_record")
@Erupt(name = "检验记录",
//        dataProxy = MesInspectionRecordService.class,
        orderBy = "MesInspectionRecord.createTime desc",
        filter = @Filter(value = "MesInspectionRecord.tenantId",
                params = {"and MesInspectionRecord.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_inspection_record set deleted = true where id = ?")
public class MesInspectionRecord extends HyperModelCreatorVo {

    private static final long serialVersionUID = -4565361834713677617L;
    @EruptField(
            views = @View(title = "检验单号"),
            edit = @Edit(title = "检验单号", notNull = true, search = @Search(vague = true))
    )
    private String sn;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", search = @Search(vague = true))
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "检验类型"),
            edit = @Edit(title = "检验类型", notNull = true, search = @Search, type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "来料检验", value = "1"),
                            @VL(label = "产品检验", value = "2"),
                            @VL(label = "出货检验", value = "3"),
                            @VL(label = "首检", value = "4"),
                            @VL(label = "巡检", value = "5")
                    }))
    )
    private Integer type;

    @ManyToOne
    @EruptField(
            views = {@View(title = "设备名称", column = "name"),
                    @View(title = "设备编码", column = "code")},
            edit = @Edit(title = "设备名称", search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == 4"))
    )
    private MesEquipment equipment;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产任务", column = "code"),
            edit = @Edit(title = "生产任务", search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail productionScheduleDetail;

    @ManyToOne
    @EruptField(
            views = @View(title = "工位", column = "name"),
            edit = @Edit(title = "工位", type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == 5"))
    )
    private MesResource resource;

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

    @EruptField(
            views = @View(title = "租户", show = false)
    )
    private Long tenantId;

    @EruptField(
            views = @View(title = "巡检开始时间"),
            edit = @Edit(title = "巡检开始时间", search = @Search(vague = true))
    )
    private String startTime;

    @EruptField(
            views = @View(title = "巡检结束时间"),
            edit = @Edit(title = "巡检结束时间", search = @Search(vague = true))
    )
    private String endTime;

    private Boolean deleted = false;

}
