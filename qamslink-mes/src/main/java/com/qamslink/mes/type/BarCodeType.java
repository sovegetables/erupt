package com.qamslink.mes.type;

import com.qamslink.mes.core.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum BarCodeType implements IEnum<Integer> {
    NOT(0, ""),
    // 产品批次条码
    PRODUCT_BATCH_CODE(1, "产品条码"),
    // 物料条码
    STOCK_CODE(2, "物料条码"),
    // 废料条码
    SCRAP_BATCH_CODE(3, "废料条码"),
    // 历史产品条码
    HISTORY_BATCH_CODE(4, "历史产品条码"),
    // 委外工单
    OUT_ORDER(5, "委外条码"),
    // 销售退货条码
    SALE_RETURN_CODE(6, "销售退货条码");
    private final int value;
    private final String label;

    BarCodeType(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(BarCodeType.values())
                    .stream().filter(i-> !Objects.equals(i.getValue(), NOT.getValueStr()))
                    .collect(Collectors.toList());
        }
    }
}
