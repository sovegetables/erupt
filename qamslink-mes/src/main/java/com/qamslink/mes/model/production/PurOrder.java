package com.qamslink.mes.model.production;

import com.qamslink.mes.core.OrderCodeGenerator;
import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.type.TicketType;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptOrg;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pur_order", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Setter
@Getter
@Erupt(name = "采购订单",
        dataProxy = PurOrder.PurOrderDataProxy.class,
        orderBy = "PurOrder.createTime desc"
)
public class PurOrder extends HyperModelVo {

    public static class PurOrderDataProxy implements DataProxy<PurOrder> {

        public void addBehavior(PurOrder purOrder) {
            purOrder.setOrderDate(new Date());
            HashSet<PurOrderStock> purOrderStocks = new HashSet<>();
            PurOrderStock orderStock = new PurOrderStock();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = LocalDate.now().plusDays(2);
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            orderStock.setEndDate(date);
            orderStock.setPrice(BigDecimal.ZERO);
            orderStock.setSerialNum(1);
            purOrderStocks.add(orderStock);
            purOrder.setOrderStocks(purOrderStocks);
        }

    }

    // 单据类型
    private Integer type = 1;

    // 业务类型
    private Integer businessType = 1;

    @EruptField(
            views = {@View(title = "单据编号", highlight = true)},
            edit = @Edit(title = "单据编号", placeHolder = "保存时自动生成", search = @Search(vague = true))
    )
    @CodeGenerator(handler = OrderCodeGenerator.class,
            params = {@CodeGenerator.KEY(key = "orderType", value = TicketType.CODE_PURCHASE_ORDER + "")})
    private String code;

    @EruptField(
            views = {@View(title = "单据日期")},
            edit = @Edit(title = "单据日期",
                    search = @Search(vague = true),
                    notNull = true,
                    type = EditType.DATE)
    )
    private Date orderDate;

    @ManyToOne
    @EruptField(
            views = {@View(title = "供应商", column = "name")},
            edit = @Edit(title = "供应商",notNull = true,
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE)
    )
    private MesCustomer supplier;

    @ManyToOne
    @EruptField(
            views = {@View(title = "采购部门", column = "name")},
            edit = @Edit(title = "采购部门",
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE)
    )
    private EruptOrg org;

//    @EruptField(
//            views = {@View(title = "单据状态")},
//            edit = @Edit(title = "单据状态",
//                    readonly = @Readonly(),
//                    editShow = false,
//                    notNull = true,
//                    type = EditType.CHOICE,
//                    choiceType = @ChoiceType(
//                    vl = {
//                        @VL(label = "暂存", value = "0"),
//                        @VL(label = "创建", value = "1"),
//                        @VL(label = "审核中", value = "2"),
//                        @VL(label = "已审核", value = "3"),
//                        @VL(label = "重新审核", value = "4"),
//                    } )
//            )
//    )
//    private Integer status = 0;

    @EruptField(
            views = {@View(title = "关闭状态")},
            edit = @Edit(
                    title = "关闭状态",
                    editShow = false,
                    readonly = @Readonly(),
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "未关闭", value = "0"),
                                    @VL(label = "已关闭", value = "1")
                            } )
            )
    )
    private Integer closeStatus = 0;

    @EruptField(
            views = {@View(title = "作废状态")},
            edit = @Edit(title = "作废状态",
                    readonly = @Readonly(),
                    notNull = true,
                    editShow = false,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "未作废", value = "0"),
                                    @VL(label = "已作废", value = "1"),
                            } )
            )
    )
    private Integer invalidStatus = 0;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @JoinColumn(name = "pur_order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            edit = @Edit(title = "物料明细", type = EditType.TAB_TABLE_ADD)
    )
    private Set<PurOrderStock> orderStocks;
}
