package com.qamslink.mes.model.basic;

import com.qamslink.mes.converter.BarCodeRuleTypeConverter;
import com.qamslink.mes.converter.BarCodeTypeConverter;
import com.qamslink.mes.converter.TicketTypeConverter;
import com.qamslink.mes.type.BarCodeRuleType;
import com.qamslink.mes.type.BarCodeType;
import com.qamslink.mes.type.TicketType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_barcode_rule")
@Getter
@Setter
@Erupt(name = "条码规则",
//        dataProxy = MesBarcodeRuleService.class,
        orderBy = "MesBarcodeRule.createTime desc",
        filter = @Filter(value = "MesBarcodeRule.tenantId",
                params = {"and MesBarcodeRule.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_barcode_rule set deleted = true where id = ?")
public class MesBarcodeRule extends HyperModelVo {

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", notNull = true, type = EditType.CHOICE, search = @Search,
                    choiceType = @ChoiceType(fetchHandler = BarCodeRuleType.Handler.class))
    )
    @Convert(converter = BarCodeRuleTypeConverter.class)
    private BarCodeRuleType categoryType;

    @EruptField(
            views = @View(title = "条码类型"),
            edit = @Edit(title = "条码类型",
                    notNull = true,
                    type = EditType.CHOICE,
                    search = @Search(value = false),
                    show = false,
                    showBy = @ShowBy(dependField = "categoryType", expr = "value == " + BarCodeRuleType.CATEGORY_TYPE_BAR_VALUE),
                    choiceType = @ChoiceType(fetchHandler = BarCodeType.Handler.class))
    )
    @Convert(converter = BarCodeTypeConverter.class)
    private BarCodeType type;

    @EruptField(
            views = @View(title = "单据类型"),
            edit = @Edit(
                    title = "单据类型",
                    notNull = true,
                    type = EditType.CHOICE,
                    search = @Search(value = false),
                    show = false,
                    showBy = @ShowBy(dependField = "categoryType", expr = "value == " + BarCodeRuleType.CATEGORY_TYPE_ORDER_VALUE),
                    choiceType = @ChoiceType(fetchHandler = TicketType.Handler.class)
            )
    )
    @Convert(converter = TicketTypeConverter.class)
    private TicketType categoryCode;

    @EruptField(
            views = @View(title = "版本号"),
            edit = @Edit(title = "版本号", notNull = true)
    )
    private String version;

    @EruptField(
            views = @View(title = "前缀"),
            edit = @Edit(title = "前缀", notNull = true)
    )
    private String prefix;

    @EruptField(
            views = @View(title = "中间规则"),
            edit = @Edit(title = "中间规则", notNull = true, type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "年月日", value = "1")
                            }
                    ))
    )
    private Integer middle;

    @EruptField(
            views = @View(title = "后缀"),
            edit = @Edit(title = "后缀")
    )
    private String suffix;

    @EruptField(
            views = @View(title = "序号长度"),
            edit = @Edit(title = "序号长度", notNull = true)
    )
    private Integer serialnoLength;

    @EruptField(
            views = @View(title = "序号最大值"),
            edit = @Edit(title = "序号最大值", notNull = true)
    )
    private Integer serialnoMax;

    @EruptField(
            views = @View(title = "序号最小值"),
            edit = @Edit(title = "序号最小值", notNull = true)
    )
    private Integer serialnoMin;

    @EruptField(
            views = @View(title = "递增值"),
            edit = @Edit(title = "递增值")
    )
    private Integer incremental;

    @EruptField(
            views = @View(title = "当前序号"),
            edit = @Edit(title = "当前序号", show = false)
    )
    private Integer currentIndex = 0;

    @EruptField(
            views = @View(title = "是否启用"),
            edit = @Edit(title = "是否启用", notNull = true, search = @Search(vague = true),
                type = EditType.BOOLEAN, boolType = @BoolType(trueText = "是", falseText = "否")
            )
    )
    private Boolean isUsed = true;



    private Boolean deleted = false;
}
