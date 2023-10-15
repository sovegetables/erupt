package com.qamslink.mes.model.quality;

import com.qamslink.mes.converter.InspectionStatusConverter;
import com.qamslink.mes.converter.InspectionTypeConverter;
import com.qamslink.mes.model.production.MesProductionScheduleDetail;
import com.qamslink.mes.type.InspectionStatus;
import com.qamslink.mes.type.InspectionType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
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
@Table(name = "mes_production_inspection_record")
@Erupt(name = "生产检验记录",
//        dataProxy = MesProductionInspectionRecordService.class,
        orderBy = "MesProductionInspectionRecord.createTime desc",
        filter = @Filter(value = "MesProductionInspectionRecord.tenantId",
                params = {"and MesProductionInspectionRecord.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        rowOperation = {
                @RowOperation(
                        title = "生成报废",
                        type = RowOperation.Type.TPL,
                        tpl = @Tpl(
                                path = "/tpl/generateScrapOrder(Production).html"
                        )
//                        ,
//                        tplWidth = "70%"
                ),
                @RowOperation(
                        title = "生成返工工单",
                        type = RowOperation.Type.TPL,
                        tpl = @Tpl(
                                path = "/tpl/rework.html"
                        )
//                        ,
//                        tplWidth = "70%"
                )
        }
)
@SQLDelete(sql = "update mes_production_inspection_record set deleted = true where id = ?")
public class MesProductionInspectionRecord extends HyperModelVo {

    private static final long serialVersionUID = -539594804177088426L;
    @EruptField(
            views = @View(title = "检验单号"),
            edit = @Edit(title = "检验单号", notNull = true, search = @Search(vague = true))
    )
    private String sn;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产任务", column = "code"),
            edit = @Edit(title = "生产任务", search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail productionScheduleDetail;

    @EruptField(
            views = @View(title = "检验数量"),
            edit = @Edit(title = "检验数量", notNull = true, readonly = @Readonly, numberType = @NumberType(min = 0))
    )
    private BigDecimal inspectionNum;

    @EruptField(
            views = @View(title = "检验类型"),
            edit = @Edit(title = "检验类型", type = EditType.CHOICE, notNull = true, search = @Search,
                    choiceType = @ChoiceType(fetchHandler = InspectionType.Handler.class))
    )
    @Convert(converter = InspectionTypeConverter.class)
    private InspectionType inspectionType;

    @EruptField(
            views = @View(title = "检验结果"),
            edit = @Edit(title = "检验结果", notNull = true, search = @Search(vague = true), boolType = @BoolType(trueText = "合格", falseText = "不合格"))
    )
    private Boolean result;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE, choiceType = @ChoiceType(
                    fetchHandler = InspectionStatus.Handler.class
            ))
    )
    @Convert(converter = InspectionStatusConverter.class)
    private InspectionStatus status = InspectionStatus.FINISHED;

    @EruptField(
            views = @View(title = "合格数"),
            edit = @Edit(title = "合格数", readonly = @Readonly, numberType = @NumberType(min = 0))
    )
    private BigDecimal qualifiedCount;

    @EruptField(
            views = @View(title = "不合格数"),
            edit = @Edit(title = "不合格数", readonly = @Readonly, numberType = @NumberType(min = 0))
    )
    private BigDecimal unqualifiedCount;

    @EruptField(
            views = @View(title = "返工数量"),
            edit = @Edit(title = "返工数量", readonly = @Readonly, numberType = @NumberType(min = 0))
    )
    private Integer backWorkNum;

    @JoinColumn(name = "production_inspection_record_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesProductionInspectionRecordDetail> productionInspectionRecordDetails;
    private Boolean deleted = false;
}
