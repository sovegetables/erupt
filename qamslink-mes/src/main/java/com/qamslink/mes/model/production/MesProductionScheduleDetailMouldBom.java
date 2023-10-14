package com.qamslink.mes.model.production;

import com.qamslink.mes.model.tool.MesMouldBomComponent;
import com.qamslink.mes.model.tool.MesMouldComponent;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "mes_mould_bom")
@Setter
@Getter
@Erupt(name = "工具BOM"
//        , dataProxy = MesProductionScheduleDetailMouldBomService.class
)
public class MesProductionScheduleDetailMouldBom extends BaseModel {

    @EruptField(
            views = @View(title = "工具BOM名称"),
            edit = @Edit(title = "工具BOM名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

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
}
