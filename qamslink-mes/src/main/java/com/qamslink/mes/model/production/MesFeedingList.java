package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mes_feeding_list")
@Setter
@Getter
@Erupt(name = "上料清单",
//        dataProxy = MesFeedingListService.class,
        orderBy = "MesFeedingList.createTime desc",
        power = @Power(add = false, delete = false),
        filter = @Filter(value = "MesFeedingList.tenantId",
                params = {"and MesFeedingList.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_feeding_list set deleted = true where id = ?")
public class MesFeedingList extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code")
            },
            edit = @Edit(title = "物料名称", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "生产工单号", column = "orderCode")
            },
            edit = @Edit(title = "生产工单", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"),
                    notNull = true, search = @Search(vague = true))
    )
    private MesWorkOrder workOrder;

    @EruptField(
            views = @View(title = "版本号"),
            edit = @Edit(title = "版本号", notNull = true)
    )
    private String version;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "是否默认", show = false),
            edit = @Edit(title = "是否默认", boolType = @BoolType(trueText = "是", falseText = "否"))
    )

    private Boolean isDefault;

    @EruptField(
            views = @View(title = "是否启用", show = false),
            edit = @Edit(title = "是否启用", boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean isUsed = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "feeding_list_id")
    @EruptField(
            views = @View(title = "上料清单明细"),
            edit = @Edit(title = "上料清单明细", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesFeedingListDetail> feedingListDetails;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )
    private Long tenantId;

    @Transient
    private Long workOrderId;

    private Boolean deleted = false;
}
