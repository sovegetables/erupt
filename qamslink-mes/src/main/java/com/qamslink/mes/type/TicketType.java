package com.qamslink.mes.type;

import xyz.erupt.core.query.IEnum;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;

import java.util.List;
import java.util.stream.Collectors;

public enum TicketType implements IEnum<Integer> {
    NOT(TicketType.CODE_NOT, ""),
    FEEDING(TicketType.CODE_FEEDING, "生产领料单"),
    MO_ORDER(TicketType.CODE_MO_ORDER, "生产订单"),
    SALE_ORDER(TicketType.CODE_SALE_ORDER, "销售订单"),
    PURCHASE_ORDER(TicketType.CODE_PURCHASE_ORDER, "采购订单"),
    SALE_OUT_ORDER(TicketType.CODE_SALE_OUT_ORDER, "成品出库单"),
    PURCHASE_IN_ORDER(TicketType.CODE_PURCHASE_IN_ORDER, "采购入库单"),
    PRODUCT_IN_ORDER(TicketType.CODE_PRODUCT_IN_ORDER, "成品入库单"),
    SUPPLIER_OUT(TicketType.CODE_SUPPLIER_OUT, "送货单"),
    PURCHASE_RECEIVE(TicketType.CODE_PURCHASE_RECEIVE, "采购到货单"),
    OTHER_IN(TicketType.CODE_OTHER_IN, "其他入库单"),
    OTHER_OUT(TicketType.CODE_OTHER_OUT, "其他出库单");

    private static final int CODE_NOT = 0;
    /**
     * 生产领料单
     */
    public static final int CODE_FEEDING = 1;
    /**
     * 生产订单
     */
    public static final int CODE_MO_ORDER = 2;
    /**
     * 采购订单
     */
    public static final int CODE_PURCHASE_ORDER = 3;
    /**
     * 采购入库单
     */
    public static final int CODE_PURCHASE_IN_ORDER = 4;
    /**
     * 销售订单
     */
    public static final int CODE_SALE_ORDER = 5;
    /**
     * 成品出库单
     */
    public static final int CODE_SALE_OUT_ORDER = 6;
    /**
     * 成品入库单
     */
    public static final int CODE_PRODUCT_IN_ORDER = 7;
    /**
     * 采购送货单
     */
    public static final int CODE_SUPPLIER_OUT = 8;

    /**
     * 采购到货单
     */
    public static final int CODE_PURCHASE_RECEIVE = 9;

    /**
     *  其他入库
     */
    public static final int CODE_OTHER_IN = 10;

    /**
     *  其他出库
     */
    public static final int CODE_OTHER_OUT = 11;

    private final int value;
    private final String label;


    TicketType(int value, String label) {
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
            return IEnum.HandlerHelper.createFetchHandlers(TicketType.values())
                    .stream().filter(i-> !i.getValue().equals(NOT.getValueStr())).collect(Collectors.toList());
        }
    }
}
