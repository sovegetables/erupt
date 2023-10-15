package com.qamslink.mes.model.outOrder;

import com.qamslink.mes.model.basic.MesStock;
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
import java.util.Set;

/**
 * @author LiYongGui
 * @create 2023-06-18 下午7:22
 */

@Entity
@Table(name = "mes_out_order_material")
@Setter
@Getter
@Erupt(name = "委外发料单",
//        dataProxy = MesOutOrderMaterialService.class,
        orderBy = "MesOutOrderMaterial.createTime desc",
        filter = @Filter(
                value = "MesOutOrderMaterial.tenantId",
                params = {"and MesOutOrderMaterial.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_out_order_material set deleted = true where id = ?")
public class MesOutOrderMaterial extends HyperModelVo {

        @EruptField(
                views = @View(title = "委外发料单号"),
                edit = @Edit(title = "委外发料单号", search = @Search)
        )
        private String code;

        @ManyToOne
        @EruptField(
                views = {
                        @View(title = "委外工单号",column = "code")
                },
                edit = @Edit(title = "委外工单号", notNull = true, type = EditType.REFERENCE_TABLE,
                        search = @Search, referenceTableType = @ReferenceTableType(label = "code"))
        )
        private MesOutOrder outOrder;

        @ManyToOne
        @EruptField(
                views = {@View(title = "产品", column = "name"),
                        @View(title = "产品编码", column = "code")},
                edit = @Edit(title = "产品", notNull = true, type = EditType.REFERENCE_TABLE)
        )
        private MesStock mainStock;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "out_order_material_id")
        @EruptField(
                views = @View(title = "物料"),
                edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
        )
        private Set<MesOutOrderMaterialDetail> outOrderMaterialDetails;

        @EruptField(
                views = @View(title = "状态"),
                edit = @Edit(title = "状态", type = EditType.CHOICE,readonly = @Readonly(),search = @Search(vague = false),
                        choiceType = @ChoiceType (
                                vl = {
                                        @VL(label = "未出库", value = "0"),
                                        @VL(label = "部分出库", value = "1"),
                                        @VL(label = "全部出库", value = "2")
                                }
                        )
                )
        )
        private Integer status = 0;
        @EruptField
        private Boolean deleted = false;
}
