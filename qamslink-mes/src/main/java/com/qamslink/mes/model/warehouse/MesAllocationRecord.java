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
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mes_allocation_record")
@Setter
@Getter
@Erupt(name = "调拨单",
//        dataProxy = MesAllocationRecordService.class,
        orderBy = "MesAllocationRecord.createTime desc",
        filter = @Filter(value = "MesAllocationRecord.tenantId",
                params = {"and MesAllocationRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_allocation_record set deleted = true where id = ?")
public class MesAllocationRecord extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "调拨单号"),
            edit = @Edit(title = "调拨单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

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
                    @View(title = "调入仓库", column = "name")
            },
            edit = @Edit(title = "调入仓库", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "name"))

    )
    private MesWarehouse inWarehouse;

    @EruptField(
            views = @View(title = "使用日期"),
            edit = @Edit(title = "使用日期")
    )
    private Date useDate;
    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", readonly = @Readonly(), type = EditType.CHOICE, search = @Search(vague = false),
                    choiceType =
                    @ChoiceType(type = ChoiceType.Type.SELECT, vl = {
                            @VL(label = "待调拨", value = "0"),
                            @VL(label = "调拨中", value = "1"),
                            @VL(label = "出库完成", value = "2"),
                            @VL(label = "调拨完成", value = "3")
                    }))
    )
    private Integer status = 0;
    @EruptField(
            views = @View(title = "已调出总数"),
            edit = @Edit(title = "已调出总数", type = EditType.NUMBER , readonly = @Readonly())
    )
    private Long allocationOutNum;

    @EruptField(
            views = @View(title = "已调入总数"),
            edit = @Edit(title = "已调入总数", type = EditType.NUMBER , readonly = @Readonly())
    )
    private Long allocationInNum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "allocation_record_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料",notNull = true, type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesAllocationRecordDetail> allocationRecordDetails;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "allocation_record_id")
    @EruptField(
            views = @View(title = "已出库条码"),
            edit = @Edit(title = "已出库条码", type = EditType.TAB_TABLE_ADD, readonly = @Readonly))

    private Set<MesAllocationOut> mesAllocationOuts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "allocation_record_id")
    @EruptField(
            views = @View(title = "已入库条码"),
            edit = @Edit(title = "已入库条码", type = EditType.TAB_TABLE_ADD, readonly = @Readonly))

    private Set<MesAllocationIn> mesAllocationIns;

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
                    @View(title = "调入库位", column = "locationCode")
            },
            edit = @Edit(title = "调入库位", search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))

    )
    private MesLocation inLocation;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产工单", column = "orderCode"),
            edit = @Edit(title = "生产工单", search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )


    private Boolean deleted = false;
}
