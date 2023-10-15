package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.production.MesWorkOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "mes_picking_record")
@Erupt(name = "领料单",
//        dataProxy = MesPickingRecordService.class,
        orderBy = "MesPickingRecord.createTime desc",
        filter = @Filter(value = "MesPickingRecord.tenantId",
                params = {"and MesPickingRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_picking_record set deleted = true where id = ?")
public class MesPickingRecord extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "生产工单", column = "orderCode"),
            edit = @Edit(title = "生产工单", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @EruptField(
            views = @View(title = "领料单号"),
            edit = @Edit(title = "领料单号", notNull = true, search = @Search(vague = true))
    )
    private String pickingCode;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "调出仓库", column = "name")
            },
            edit = @Edit(title = "调出仓库", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "name"))

    )
    private MesWarehouse outWarehouse;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "调出库位", column = "locationCode")
            },
            edit = @Edit(title = "调出库位", search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))

    )
    private MesLocation outLocation;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "调入仓库", column = "name")
            },
            edit = @Edit(title = "调入仓库", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "name"))

    )
    private MesWarehouse inWarehouse;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "调入库位", column = "locationCode"),
            },
            edit = @Edit(title = "调入库位", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))

    )
    private MesLocation inLocation;

    @EruptField(
            views = @View(title = "使用日期"),
            edit = @Edit(title = "使用日期")
    )
    private Date useDate;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", readonly = @Readonly(), type = EditType.CHOICE, search = @Search(vague = true),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "待调拨", value = "0"),
                            @VL(label = "调拨中", value = "1"),
                            @VL(label = "出库完成", value = "2"),
                            @VL(label = "调拨完成", value = "3")
                    }))
    )
    private Integer status = 0;

    // 是否已出库完成（0-未完成 1-已完成）
    private Integer pickingStatus = 0;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "picking_record_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesPickingRecordDetail> pickingRecordDetails;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "picking_record_id")
    @EruptField(
            views = @View(title = "领入记录"),
            edit = @Edit(title = "领入记录", readonly = @Readonly, type = EditType.TAB_TABLE_ADD)
    )
    private List<MesPickingIn> pickingIns;
    private Boolean deleted = false;
}
