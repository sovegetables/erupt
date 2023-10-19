package com.qamslink.mes.model.basic;

import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="mes_customer", uniqueConstraints={ @UniqueConstraint(columnNames = "code")})
@Erupt(name = "往来单位",
//        dataProxy = MesCustomerService.class,
        linkTree = @LinkTree(field = "customerType"),
        orderBy = "MesCustomer.createTime desc"
//        ,
//        rowOperation = {
//                @RowOperation(
//                        code = "customer",
//                        title = "生成供应商账号",
//                        mode = RowOperation.Mode.MULTI
//                        ,
//                        operationHandler = CustomerHandler.class
//                )
//        }
)
@Data
public class MesCustomer extends HyperModelVo {

    @EruptField(
            views = @View(
                    title = "单位编码", highlight = true
            ),
            edit = @Edit(
                    title = "单位编码",
                    type = EditType.INPUT, search = @Search(vague = true),
                    notNull = true,
                    placeHolder = "保存时自动生成",
                    inputType = @InputType
            )
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(
                    title = "单位全称"
            ),
            edit = @Edit(
                    title = "单位全称",
                    type = EditType.INPUT, search = @Search(vague = true), notNull = true,
                    inputType = @InputType
            )
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "类型", column = "name"),
            edit = @Edit(title = "类型", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesCustomerType customerType;

    @EruptField(
            views = @View(
                    title = "单位简称"
            ),
            edit = @Edit(
                    title = "单位简称",
                    type = EditType.INPUT, search = @Search(vague = true), notNull = true,
                    inputType = @InputType
            )
    )
    private String alias;

    @EruptField(
            views = @View(
                    title = "详细地址"
            ),
            edit = @Edit(
                    title = "详细地址",
                    type = EditType.TEXTAREA,
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    @EruptField(
            edit = @Edit(title = "单位联系人", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesCustomerContact> contacts;

    private Boolean deleted = false;
}
