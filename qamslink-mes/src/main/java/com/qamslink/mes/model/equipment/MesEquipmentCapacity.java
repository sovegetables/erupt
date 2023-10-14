package com.qamslink.mes.model.equipment;

import com.qamslink.mes.model.basic.MesProductLine;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.MesWorkshop;
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
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "mes_equipment_capacity")
@Erupt(name = "标准产能",
//        dataProxy = ApsCapacityService.class,
        orderBy = "createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesEquipmentCapacity.tenantId",
                params = {"and MesEquipmentCapacity.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_equipment_capacity set deleted = true where id = ?")
public class MesEquipmentCapacity extends HyperModelCreatorVo {

    public static final int TYPE_LINE = 1;
    public static final int TYPE_STOCK = 2;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "标准产线产能", value = TYPE_LINE + ""),
                            @VL(label = "标准物料产能", value = TYPE_STOCK + "")
                    })
            )
    )
    private Integer type = 1;

    @ManyToOne
    @EruptField(
            views = @View(title = "车间", column = "name"),
            edit = @Edit(title = "车间", type = EditType.REFERENCE_TABLE,
                    search = @Search(vague = true),
                    notNull = true)
    )
    private MesWorkshop workshop;

    @ManyToOne
    @EruptField(
            views = @View(title = "产线", column = "name"),
            edit = @Edit(title = "产线",
                    type = EditType.REFERENCE_TABLE,
                    notNull = true,
                    search = @Search(vague = true)
            )
    )
    private MesProductLine productLine;

    @ManyToOne
    @EruptField(
            views = @View(title = "设备", column = "name"),
            edit = @Edit(title = "设备",type = EditType.REFERENCE_TABLE,
                    search = @Search(vague = true),
                    notNull = true)
    )
    private MesEquipment mesEquipment;

    @Transient
    private Long mesEquipmentId;
    @Transient
    private Long productLineId;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料类型", column = "stockCategory.name"),
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code")
            },
            edit = @Edit(title = "物料",
                    type = EditType.REFERENCE_TABLE,
                    notNull = true,
                    search = @Search(vague = true, value = false),
                    showBy = @ShowBy(dependField = "type", expr = "value == " + TYPE_STOCK)
            )
    )
    private MesStock stock;

    @Transient
    private Long stockId;

    @EruptField(
            views = @View(title = "产能", show = false),
            edit = @Edit(title = "产能", notNull = true)
    )
    private BigDecimal capacity;

    @Transient
    @EruptField(
            views = @View(title = "产能", template = "'每' + item.hour + '小时生产' + item.capacity + ' ' + item.unit")
    )
    private String produceCapacity;

    @EruptField(
            views = @View(title = "单位", show = false),
            edit = @Edit(title = "单位", notNull = true)
    )
    private String unit;

    @EruptField(
            views = @View(title = "小时", show = false),
            edit = @Edit(title = "小时", type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(value = "8", label = "8"),
                                    @VL(value = "12", label = "12"),
                                    @VL(value = "24", label = "24")
                            }
                    ))
    )
    private Integer hour = 24;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.BOOLEAN,
                    boolType = @BoolType(trueText = "已启用", falseText = "已禁用"))
    )
    private Boolean status = true;

    private Long tenantId;

    private Boolean deleted = false;
}
