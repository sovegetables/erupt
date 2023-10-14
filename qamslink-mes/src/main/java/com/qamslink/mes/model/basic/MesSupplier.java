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
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="mes_supplier")
@Erupt(name = "供应商管理",
//        dataProxy = MesSupplierService.class,
        filter = @Filter(value = "MesSupplier.tenantId",params = {"and MesSupplier.deleted = false"}, conditionHandler = TenantFilter.class))
@Getter
@Setter
@SQLDelete(sql = "update mes_supplier set deleted = true where id = ?")
public class MesSupplier extends HyperModelCreatorVo {

    @EruptField(
            views = @View(
                    title = "供应商全称"
            ),
            edit = @Edit(
                    title = "供应商全称",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String name;

    @EruptField(
            views = @View(
                    title = "供应商简称"
            ),
            edit = @Edit(
                    title = "供应商简称",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String alias;

    @EruptField(
            views = @View(
                    title = "供应商编码"
            ),
            edit = @Edit(
                    title = "供应商编码",
                    type = EditType.INPUT, search = @Search, notNull = true,
                    inputType = @InputType
            )
    )
    private String code;

    @EruptField(
            views = @View(
                    title = "详细地址"
            ),
            edit = @Edit(
                    title = "详细地址",
                    type = EditType.INPUT,
                    inputType = @InputType
            )
    )
    private String address;

    @EruptField(
            views = @View(
                    title = "备注"
            ),
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;

    @EruptField
    private Long tenantId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplier_id")
    @EruptField(
            edit = @Edit(title = "供应商联系人", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesSupplierContact> contacts;

    private Boolean deleted = false;
}
