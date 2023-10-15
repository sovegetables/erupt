package com.qamslink.mes.model.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qamslink.mes.model.basic.MesCustomer;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptOrg;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_other_in")
@Erupt(name = "其他入库",
        power = @Power(delete = false),
//        dataProxy = MesOtherInService.class,
        filter = @Filter(
                value = "MesOtherIn.tenantId",
                conditionHandler = TenantFilter.class))
public class MesOtherIn extends HyperModelVo {


    @EruptField(
            views = {@View(title = "单据号")},
            edit = @Edit(title = "单据号", readonly = @Readonly, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "普通", value = "1"),
                            @VL(label = "调拨", value = "2"),
                            @VL(label = "历史", value = "3")
                    }
            ))
    )
    private Integer type;

    @EruptField(
            views = @View(title = "入库时间"),
            edit = @Edit(title = "入库时间", notNull = true, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date inTime;

    @ManyToOne
    @EruptField(
            views = @View(title = "部门", column = "name"),
            edit = @Edit(title = "部门", type = EditType.REFERENCE_TABLE)
    )
    private EruptOrg Org;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "客户", column = "name")
            },
            edit = @Edit(title = "客户", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "id"))
    )
    private MesCustomer customer;

    @JoinColumn(name = "mes_other_in_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesOtherInDetail> mesOtherInDetails;
}
