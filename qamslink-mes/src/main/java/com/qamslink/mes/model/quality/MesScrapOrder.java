package com.qamslink.mes.model.quality;

import com.qamslink.mes.converter.ScrapOrderTypeConverter;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.production.MesWorkOrder;
import com.qamslink.mes.type.ScrapOrderType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "mes_scrap_order")
@Erupt(name = "生产报废单",
//        dataProxy = MesScrapOrderService.class,
        orderBy = "MesScrapOrder.createTime desc",
        filter = @Filter(value = "MesScrapOrder.tenantId",
                params = {"and MesScrapOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false)
)
@SQLDelete(sql = "update mes_scrap_order set deleted = true where id = ?")
public class MesScrapOrder extends HyperModelVo {
    @EruptField(
            views = @View(title = "报废单号"),
            edit = @Edit(title = "报废单号",readonly = @Readonly, search = @Search(vague = true),notNull = true)
    )
    private String sn;

    @OneToOne
    @EruptField(
            views = @View(title = "生产检验单号",column = "sn"),
            edit = @Edit(title = "生产检验单号",  search = @Search(vague = true),type = EditType.REFERENCE_TABLE
            ,referenceTableType  = @ReferenceTableType(label = "sn"))
    )
    private MesProductionInspectionRecord prodInspection;

    @OneToOne
    @EruptField(
            views = @View(title = "巡检记录单号",column = "sn"),
            edit = @Edit(title = "巡检记录单号",  search = @Search(vague = true),type = EditType.REFERENCE_TABLE
            ,referenceTableType  = @ReferenceTableType(label = "sn"))
    )
    private MesPatrolRecord inspection;
    @OneToOne
    @EruptField(
            views = @View(title = "生产工单",column = "orderCode"),
            edit = @Edit(title = "生产工单",readonly = @Readonly, notNull = true,type = EditType.REFERENCE_TABLE
            ,referenceTableType  = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @EruptField(
            views = @View(title = "报废类型"),
            edit = @Edit(title = "报废类型",readonly = @Readonly, type = EditType.CHOICE,notNull = true, search = @Search,
                    choiceType = @ChoiceType(fetchHandler = ScrapOrderType.Handler.class))
    )
    @Convert(converter = ScrapOrderTypeConverter.class)
    private ScrapOrderType inspectionType;

    @OneToOne
    @EruptField(
            views = {
                    @View(title = "报废物料",column = "name"),
                    @View(title = "物料编码",column = "code"),
                    @View(title = "规格",column = "spec"),
                    @View(title = "单位",column = "unit")
            },
            edit = @Edit(title = "报废物料",readonly = @Readonly, notNull = true, search = @Search(vague = true),type = EditType.REFERENCE_TABLE
            ,referenceTableType  = @ReferenceTableType(label = "name"))
    )
    private MesStock mesStock;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", search = @Search(vague = true), type = EditType.NUMBER)
    )
    private String num;

    @EruptField(
            views = @View(title = "报废原因"),
            edit = @Edit(title = "报废原因",  type = EditType.TEXTAREA)
    )
    private String remark;
    private Boolean deleted = false;


}
