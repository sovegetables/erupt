package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="mes_bom")
@Erupt(name = "物料清单",
//        dataProxy = MesBomService.class,
        orderBy = "MesBom.createTime desc",
        filter = @Filter(value = "MesBom.tenantId",params = {"and MesBom.deleted = false"},conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_bom set deleted = true where id = ?")
public class MesBom extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "物料清单名称"),
            edit = @Edit(title = "物料清单名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "物料清单编码"),
            edit = @Edit(title = "物料清单编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code"),
                    @View(title = "物料名称", column = "name")
            },
            edit = @Edit(title = "物料名称", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))
    )
    private MesStock mainStock;

    @EruptField(
            views = @View(title = "是否默认BOM"),
            edit = @Edit(title = "是否默认BOM", boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean isDefault = true;

    @EruptField(
            views = @View(title = "版本"),
            edit = @Edit(title = "版本")
    )
    private String version;

    @JoinColumn(name = "bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
//            ,
//            hasExtra = true
    )
    private List<MesBomStock> stocks;

    @JoinColumn(name = "bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "工序"),
            edit = @Edit(title = "工序", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesBomWorkingProcedure> bomWorkingProcedures;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
