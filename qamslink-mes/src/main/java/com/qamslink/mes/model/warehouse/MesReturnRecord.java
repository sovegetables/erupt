package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "mes_return_record")
@Erupt(name = "退货单",
//        dataProxy = MesReturnRecordService.class,
        orderBy = "MesReturnRecord.createTime desc",
        filter = @Filter(value = "MesReturnRecord.tenantId",
                params = {"and MesReturnRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_return_record set deleted = true where id = ?")
public class MesReturnRecord extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "退货单号"),
            edit = @Edit(title = "退货单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "return_record_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesReturnRecordDetail> returnRecordDetails;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )


    private Boolean deleted = false;
}
