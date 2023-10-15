package com.qamslink.mes.model.tool;

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
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "mes_mould_bom")
@Setter
@Getter
@Erupt(name = "工具清单",
//        dataProxy = MesMouldBomService.class,
        orderBy = "MesMouldBom.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesMouldBom.tenantId",
                params = {"and MesMouldBom.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_mould_bom set deleted = true where id = ?")
public class MesMouldBom extends HyperModelVo {

    @EruptField(
            views = @View(title = "工具BOM名称"),
            edit = @Edit(title = "工具BOM名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "工具BOM编码"),
            edit = @Edit(title = "工具BOM编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "构件", column = "name"),
            edit = @Edit(title = "构件", type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesMouldComponent mouldComponent;

    @EruptField(
            views = @View(title = "内径长（cm）"),
            edit = @Edit(title = "内径长（cm）", numberType = @NumberType(min = 0))
    )
    private BigDecimal internalDiameter;

    @EruptField(
            views = @View(title = "外径长（cm）"),
            edit = @Edit(title = "外径长（cm）", numberType = @NumberType(min = 0))
    )
    private BigDecimal externalDiameter;

    @EruptField(
            views = @View(title = "面积（cm²）"),
            edit = @Edit(title = "面积（cm²）", numberType = @NumberType(min = 0))
    )
    private BigDecimal area;

    @EruptField(
            views = @View(title = "最大压力（Ton）"),
            edit = @Edit(title = "最大压力（Ton）", numberType = @NumberType(min = 0))
    )
    private BigDecimal maxPressure;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @JoinColumn(name = "mould_bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "构件"),
            edit = @Edit(title = "构件", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesMouldBomComponent> mouldComponents;
    private Boolean deleted = false;
}
