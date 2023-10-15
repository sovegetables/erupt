package com.qamslink.mes.model.tool;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.warehouse.MesLocation;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mes_mould")
@Setter
@Getter
@Erupt(name = "工具列表",
//        dataProxy = MesMouldService.class,
        orderBy = "MesMould.createTime desc",
        power = @Power(importable = true),
        linkTree = @LinkTree(field = "mouldComponent"),
        filter = @Filter(value = "MesMould.tenantId",
                params = {"and MesMould.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_mould set deleted = true where id = ?")
public class MesMould extends HyperModelVo {

    @EruptField(
            views = @View(title = "工具名称"),
            edit = @Edit(title = "工具名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "工具编码"),
            edit = @Edit(title = "工具编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属构件名称", column = "name"),
            edit = @Edit(title = "所属构件名称", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE)
    )
    private MesMouldComponent mouldComponent;

    @ManyToOne
    @EruptField(
            views = @View(title = "供应商", column = "name"),
            edit = @Edit(title = "供应商", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesCustomer supplier;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "存放库位", column = "locationCode",show = false),
            },
            edit = @Edit(title = "存放库位", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    /*@EruptField(
            views = @View(title = "入库日期"),
            edit = @Edit(title = "入库日期", dateType = @DateType(type = DateType.Type.DATE))
    )
    private Date incomingDate;*/

    @EruptField(
            views = @View(title = "模穴数"),
            edit = @Edit(title = "模穴数", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal holes;

    @EruptField(
            views = @View(title = "标准使用寿命"),
            edit = @Edit(title = "标准使用寿命", numberType = @NumberType(min = 0))
    )
    private BigDecimal activeLife;

    @EruptField(
            views = @View(title = "累计使用寿命"),
            edit = @Edit(title = "累计使用寿命", numberType = @NumberType(min = 0))
    )
    private BigDecimal cumulativeActiveLife;

    @Transient
    @EruptField(
            views = @View(title = "打印", type = ViewType.LINK)
    )
    private String url;

    @ManyToMany
    @JoinTable(
            name = "mes_mould_stock",
            joinColumns = @JoinColumn(name = "mould_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "关联物料", column = "name"),
            edit = @Edit(title = "关联物料", type = EditType.TAB_TABLE_REFER)
    )
    private List<MesStock> stocks;



    private Boolean deleted = false;
}
