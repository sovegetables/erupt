package com.qamslink.mes.model.production;

import com.qamslink.mes.core.OrderCodeGenerator;
import com.qamslink.mes.model.basic.MesBom;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.type.TicketType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mes_work_order")
@Setter
@Getter
@Erupt(name = "生产工单",
//        dataProxy = MesWorkOrderService.class,
        orderBy = "MesWorkOrder.createTime desc",
        rowOperation = {
                @RowOperation(
                        title = "手动排产",
                        type = RowOperation.Type.TPL,
                        tpl = @Tpl(
                                path = "/tpl/produceOrder.html"
                        )
//                        ,
//                        tplWidth = "70%"
                ),
                @RowOperation(
                        title = "变更",
                        type = RowOperation.Type.TPL,
                        tpl = @Tpl(
                                path = "/tpl/productionChange.html"
                        )
//                        ,
//                        tplWidth = "70%"
                ),
                @RowOperation(
                        title = "自动排产"
//                        ,
//                        operationHandler = MesWorkOrderService.class
                ),
        }
)
public class MesWorkOrder extends HyperModelVo {

    public static final int STATUS_UN_START = 0;
    public static final int STATUS_SOME_START = 1;
    public static final int STATUS_FINISH_START = 2;
    public static final int STATUS_FINISH = 3;

    private static final long serialVersionUID = -1238055512968301837L;

    @EruptField(
            views = {@View(title = "单据编号", highlight = true)},
            edit = @Edit(title = "单据编号", placeHolder = "保存时自动生成" , search = @Search(vague = true))
    )
    @CodeGenerator(handler = OrderCodeGenerator.class,
            params = {@CodeGenerator.KEY(key = "orderType", value = TicketType.CODE_MO_ORDER + "")})
    private String orderCode;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", show = false, search = @Search(), type = EditType.CHOICE, choiceType = @ChoiceType(vl = {
                    @VL(label = "待排产", value = STATUS_UN_START+""),
                    @VL(label = "部分排产", value = STATUS_SOME_START + ""),
                    @VL(label = "已排产", value = STATUS_FINISH_START + ""),
                    @VL(label = "已完成", value = STATUS_FINISH + "")
            }))
    )
    private Integer status = 0;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", readonly = @Readonly, search = @Search(), type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "普通类型", value = "1"),
                            @VL(label = "返工类型", value = "2")
                    }
            ))
    )
    private Integer type = 1;

    // 排产进度
    @Transient
    @EruptField(
            views = @View(title = "排产进度", template = "item.doing + '/' + item.num")
    )
    private String progress;

    @Transient
    private BigDecimal doing = BigDecimal.ZERO;

    @ManyToOne
    @EruptField(
            views = {@View(title = "产品编码", column = "code"),
                    @View(title = "产品名称", column = "name"),
                    @View(title = "id", show = false, column = "id")},
            edit = @Edit(title = "产品编码", notNull = true,
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code")
            )
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "客户简称", column = "customer.alias"),
                    @View(title = "订单编号", column = "orderCode")
            },
            edit = @Edit(title = "客户订单号",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "orderCode"),
                    search = @Search(vague = true))
    )
    private MesCustomerOrder customerOrder;

    @ManyToOne
    @EruptField(
            edit = @Edit(title = "物料清单",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"),
                    search = @Search(vague = true)
            )
    )
    private MesBom mesBom;

    @EruptField(
            views = {@View(title = "工单数量", show = false)},
            edit = @Edit(title = "工单数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @OneToMany()
    @JoinColumn(name = "work_order_id")
//    @EruptField(
//            views = @View(title = "工序详情"),
//            edit = @Edit(title = "工序详情", notNull = true, readonly = @Readonly, type = EditType.TAB_TABLE_ADD)
//    )
    private List<MesWorkOrderProcedure> workOrderProcedures;

    @EruptField(
            views = {@View(title = "计划开始时间")},
            edit = @Edit(title = "计划开始时间", notNull = true, type = EditType.DATE)
    )
    private Date startDate;

    @EruptField(
            views = {@View(title = "计划完成时间")},
            edit = @Edit(title = "计划完成时间", notNull = true, type = EditType.DATE)
    )
    private Date endDate;

    @EruptField(
            views = {@View(title = "实际开始时间")},
            edit = @Edit(title = "实际开始时间", show = false, type = EditType.DATE)
    )
    private Date actualStartDate;

    @EruptField(
            views = {@View(title = "实际完成时间")},
            edit = @Edit(title = "实际完成时间", show = false, type = EditType.DATE)
    )
    private Date actualEndDate;

    @EruptField(
            views = {@View(title = "备注", show = false)},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @Comment("是否已排产（0-否 1-是）")
    @EruptField(
            views = {@View(title = "是否已排产")},
            edit = @Edit(title = "是否已排产", readonly = @Readonly(), search = @Search, boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean isGenerate = false;
}
