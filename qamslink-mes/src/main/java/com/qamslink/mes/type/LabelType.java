package com.qamslink.mes.type;

import com.qamslink.mes.core.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum LabelType implements IEnum<Integer> {

    NOT(LabelType.CATEGORY_NOT, ""),
    // 物料条码
    MATERIAL(LabelType.CATEGORY_MATERIAL, "物料条码"),
    // 产品条码
    PRODUCT_MATERIAL(LabelType.CATEGORY_PRODUCT_MATERIAL, "产品条码"),
    // 历史条码
    HISTORY_MATERIAL(LabelType.CATEGORY_HISTORY_MATERIAL, "历史条码"),
    // 退货条码
    RETURN_MATERIAL(LabelType.CATEGORY_RETURN_MATERIAL, "退货条码"),
    // 废料条码
    SCRAP_MATERIAL(LabelType.CATEGORY_SCRAP_MATERIAL, "废料条码"),
    // 流转卡
    FLOW_CARD(LabelType.CATEGORY_FLOW_CARD, "流转卡");
    private final int value;
    private final String label;

    private static final int CATEGORY_NOT = 0;
    private static final int CATEGORY_MATERIAL= 1;
    public static final int CATEGORY_PRODUCT_MATERIAL = 2;
    public static final int CATEGORY_HISTORY_MATERIAL = 3;
    public static final int CATEGORY_RETURN_MATERIAL = 4;
    public static final int CATEGORY_FLOW_CARD = 5;
    public static final int CATEGORY_SCRAP_MATERIAL = 6;

    LabelType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getValueStr() {
        return String.valueOf(value);
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Component
    public static class Handler implements ChoiceFetchHandler{
        @Override
        public List<VLModel> fetch(String[] params) {
            return HandlerHelper.createFetchHandlers(LabelType.values())
                    .stream().filter(i-> !Objects.equals(i.getValue(), NOT.getValueStr()))
                    .collect(Collectors.toList());
        }
    }
}
