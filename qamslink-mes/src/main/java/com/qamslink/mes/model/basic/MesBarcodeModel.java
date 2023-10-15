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
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mes_barcode_model")
@Getter
@Setter
@Erupt(name = "标签模板",
//        dataProxy = MesBarcodeModelService.class,
        orderBy = "MesBarcodeModel.createTime desc",
        filter = @Filter(value = "MesBarcodeModel.tenantId",
                params = {"and MesBarcodeModel.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_barcode_model set deleted = true where id = ?")
public class MesBarcodeModel extends HyperModelVo {

    @EruptField(
            views = @View(title = "模板名称"),
            edit = @Edit(title = "模板名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "宽度"),
            edit = @Edit(title = "宽度", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal width;

    @EruptField(
            views = @View(title = "高度"),
            edit = @Edit(title = "高度", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal height;

    @EruptField(
            views = @View(title = "条码模板", show = false),
            edit = @Edit(title = "条码模板", notNull = true, type = EditType.CODE_EDITOR)
    )
    private String detail;



    private Boolean deleted = false;
}
