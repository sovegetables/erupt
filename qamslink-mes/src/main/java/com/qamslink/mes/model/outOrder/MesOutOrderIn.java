package com.qamslink.mes.model.outOrder;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.warehouse.MesWarehouse;
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
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author LiYongGui
 * @create 2023-06-07 下午2:00
 */
@Setter
@Getter
@Entity
@Table(name = "mes_out_order_in")
@Erupt(name = "委外入库单",
//        dataProxy = MesOutOrderInService.class,
        orderBy = "MesOutOrderIn.createTime desc",
        filter = @Filter(value = "MesOutOrderIn.tenantId",
                params = {"and MesOutOrderIn.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_out_order_in set deleted = true where id = ?")
public class MesOutOrderIn extends HyperModelVo {
    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true, show = false, search = @Search(vague = true))
    )
    private String barcode;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "产品编码", column = "name", type = ViewType.TEXT),
                    @View(title = "产品名称", column = "code", type = ViewType.TEXT),
            },
            edit = @Edit(title = "产品名称", show = false, search = @Search(vague = true))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "入库数量",type = ViewType.TEXT)
    )
    private String inCapacity;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "仓库", column = "name"),
            },
            edit = @Edit(title = "仓库", show = false, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse mesWarehouse;

    @EruptField(
            views = @View(title = "库位",type = ViewType.TEXT)
    )
    private String locationCode;

    @EruptField(
            views = @View(title = "委外工单号", type = ViewType.TEXT),
            edit = @Edit(title = "委外工单号", show = false, search = @Search(vague = true))
    )
    private String outOrderCode;

    @EruptField(
            views = @View(title = "入库时间",type = ViewType.DATE_TIME),
            edit = @Edit(title = "入库时间", show = false, search = @Search(vague = true))
    )
    private Date inTime;

    @EruptField(
            views = @View(title = "入库人员",type = ViewType.TEXT)
    )
    private String depositor;

    @EruptField(
            views = @View(title = "备注")
    )
    private String remark;

    

    private Boolean deleted = false;
}
