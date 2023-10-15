package com.qamslink.mes.model.production;

import com.qamslink.mes.core.EruptDataAdapterService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.core.annotation.EruptDataProcessor;
import xyz.erupt.core.invoke.DataProcessorManager;
import xyz.erupt.core.query.EruptQuery;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.core.view.Page;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static xyz.erupt.annotation.config.QueryExpression.EQ;
import static xyz.erupt.annotation.config.QueryExpression.LIKE;

@Erupt(name = "流转卡记录",
        dataProxy = MesFlowCardDetail.InnerDataProxy.class,
        orderBy = "MesFlowCardDetail.updateTime desc",
        filter = @Filter(
                value = "MesFlowCardDetail.tenantId",
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false, delete = false, edit = false)
)
@EruptDataProcessor("mes-flow-card")
public class MesFlowCardDetail extends BaseModel {

    @EruptField(
            views = {@View(title = "流转卡号")},
            edit = @Edit(title = "流转卡号", search = @Search(vague = true), notNull = true)
    )
    private String code;

    @EruptField(
            views = {@View(title = "生产工单号")},
            edit = @Edit(title = "生产工单号", search = @Search(vague = true), notNull = true)
    )
    private String workOrderCode;

    @EruptField(
            views = {
                    @View(title = "当前工序")
            },
            edit = @Edit(title = "当前工序", notNull = true, search = @Search(vague = true))
    )
    private String workProcedureName;

    @EruptField(
            views = {
                    @View(title = "未报工数量")
            },
            edit = @Edit(title = "未报工数量", notNull = true)
    )
    private BigDecimal unFinishNum;

    @EruptField(
            views = {
                    @View(title = "已报工数量")
            },
            edit = @Edit(title = "已报工数量", notNull = true)
    )
    private BigDecimal finishNum;


    @EruptField(
            views = {
                    @View(title = "排产数量")
            },
            edit = @Edit(title = "排产数量", notNull = true)
    )
    private BigDecimal taskFinishNum;

    @EruptField(
            views = {
                    @View(title = "物料名称")
            },
            edit = @Edit(title = "物料名称", notNull = true, search = @Search)
    )
    private String stockName;

    @EruptField(
            views = {
                    @View(title = "物料编码")
            },
            edit = @Edit(title = "物料编码", notNull = true, search = @Search)
    )
    private String stockCode;

    @EruptField(
            views = {
                    @View(title = "物料单位")
            },
            edit = @Edit(title = "物料单位", notNull = true)
    )
    private String stockUnit;

    @EruptField(
            views = @View(title = "检验接收状态"),
            edit = @Edit(title = "检验接收状态", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "无", value = "0"),
                            @VL(label = "已检入", value = "1"),
                            @VL(label = "已检出", value = "2")
                    }))
    )
    private Integer receiveStatus;

    @EruptField(
            views = @View(title = "是否进行生产检验"),
            edit = @Edit(title = "是否进行生产检验", type = EditType.BOOLEAN, notNull = true,
                    boolType = @BoolType(
                            falseText = "未检验",
                            trueText = "已检验"
                    ))
    )
    private Boolean productionInspectionStatus;

    @EruptField(
            views = @View(title = "不合格数")
    )
    private BigDecimal unqualifiedCount;

    @EruptField(
            views = {
                    @View(title = "创建时间")
            },
            edit = @Edit(title = "创建时间", notNull = true)
    )
    @Comment("创建时间")
    private Date createTime;

    @EruptField(
            views = {
                    @View(title = "更新时间")
            },
            edit = @Edit(title = "更新时间", notNull = true)
    )
    @Comment("更新时间")
    private Date updateTime;



    static {
        DataProcessorManager.register("mes-flow-card", FlowCardDataProcessor.class);
    }

    @Service
    public static class FlowCardDataProcessor extends EruptDataAdapterService {

//        @Autowired
//        @Lazy
//        private MesFlowCardDetailService flowCardDetailService;

        @Autowired
        private EruptUserService eruptUserService;

        @Data
        public static class QueryDTO {

            private int pageNum;

            private int pageSize;

            private String flowCardCode;

            private String workOrderCode;

            private String workingProcedureName;

            private String stockName;

            private String stockCode;

            private Long tenantId;
        }

        @Override
        public Page queryList(EruptModel eruptModel, Page page, EruptQuery eruptQuery) {
            EruptUser currentEruptUser = eruptUserService.getCurrentEruptUser();
//            Long tenantId = currentEruptUser.getTenantId();
            QueryDTO queryDTO = new QueryDTO();
            queryDTO.setPageNum(page.getPageIndex());
            queryDTO.setPageSize(page.getPageSize());
//            queryDTO.setTenantId(tenantId);
            List<Condition> conditions = eruptQuery.getConditions();
            for (Condition condition : conditions) {
                String conditionKey = condition.getKey();
                String queryString = condition.getValue().toString();
                Map<String, Consumer<String>> propertyMap = new HashMap<>();
                propertyMap.put("code", queryDTO::setFlowCardCode);  // 映射条件"code"到setFlowCardCode方法
                propertyMap.put("workOrderCode", queryDTO::setWorkOrderCode);  // 映射条件"orderCode"到setWorkOrderCode方法
                propertyMap.put("workProcedureName", queryDTO::setWorkingProcedureName);  // 映射条件"workProcedureName"到setWorkingProcedureName方法
                propertyMap.put("stockName", queryDTO::setStockName);  // 映射条件"stockName"到setStockName方法
                propertyMap.put("stockCode", queryDTO::setStockCode);  // 映射条件"stockCode"到setStockCode方法
                if (condition.getExpression() == EQ || condition.getExpression() == LIKE) {
                    Consumer<String> propertySetter = propertyMap.get(conditionKey);  // 获取对应条件的属性设置器
                    if (propertySetter != null) {
                        propertySetter.accept(queryString);  // 使用属性设置器设置属性值
                    }
                }
            }
//            List<MesFlowCardDetailEntity> records = flowCardDetailService.selectPage(queryDTO);
//            int pageTotal = flowCardDetailService.pageCount(queryDTO);
//            if (CollUtil.isNotEmpty(records)) {
//                List<Map<String, Object>> list = records.stream()
//                        .map(BeanUtil::beanToMap)
//                        .collect(Collectors.toList());
//                list.forEach(i -> {
//                    BigDecimal unqualifiedCount = (BigDecimal) i.get("unqualifiedCount");
//                    if (unqualifiedCount == null) {
//                        i.put("productionInspectionStatus", false);
//                    } else {
//                        i.put("productionInspectionStatus", true);
//                    }
//                });
//                page.setList(list);
//            } else {
//                page.setList(new ArrayList<>());
//            }
//            page.setTotal((long) pageTotal);
            return page;
        }
    }

    public static class InnerDataProxy implements DataProxy<MesFlowCardDetail> {
    }

}

