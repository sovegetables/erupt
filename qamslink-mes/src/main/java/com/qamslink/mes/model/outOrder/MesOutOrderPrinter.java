package com.qamslink.mes.model.outOrder;

import com.qamslink.mes.model.basic.MesCustomer;
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
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author LiYongGui
 * @create 2023-06-07 下午5:48
 */

@Setter
@Getter
@Entity
@Table(name = "mes_out_order_printer")
@Erupt(name = "委外条码打印",
//        dataProxy = MesOutOrderPrinterService.class,
        orderBy = "MesOutOrderPrinter.createTime desc",
        filter = @Filter(value = "MesOutOrderPrinter.tenantId",
                params = {"and MesOutOrderPrinter.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_out_order_printer set deleted = true where id = ?")
public class MesOutOrderPrinter extends HyperModelVo {

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true, search = @Search(vague = true))
    )
    private String barcode;

    @ManyToOne
    @EruptField(
            views = @View(title = "委外工单", column = "code"),
            edit = @Edit(title = "委外工单", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesOutOrder outOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "产品名称", column = "name"),
                    @View(title = "产品编码", column = "code")
            },
            edit = @Edit(title = "产品名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "总数量")
    )
    private BigDecimal scheduledNum;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "进行中", value = "0"),
                            @VL(label = "已完成", value = "1")
                    }
            ))
    )
    private Integer status;

    @ManyToOne
    @EruptField(
            views = @View(title = "供应商简称", column = "alias"),
            edit = @Edit(title = "供应商", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesCustomer customer;

    @EruptField(
            views = @View(title = "打印机")
    )
    private String printerName;

    @EruptField(
            views = @View(title = "备注")
    )
    private String remark;

    private Boolean deleted = false;
}
