package com.qamslink.mes.model.basic;

import com.qamslink.mes.converter.BarCodeTypeConverter;
import com.qamslink.mes.type.BarCodeType;
import com.qamslink.mes.core.EruptDataAdapterService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
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
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import javax.persistence.Convert;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.qamslink.mes.model.warehouse.MesStockBarcodePrintDetail.STATUS_IN;
import static com.qamslink.mes.model.warehouse.MesStockBarcodePrintDetail.STATUS_OUT;
import static xyz.erupt.annotation.config.QueryExpression.EQ;
import static xyz.erupt.annotation.config.QueryExpression.LIKE;

@EqualsAndHashCode(callSuper = true)
@Erupt(name = "条码流水记录",
        dataProxy = BarcodeList.InnerDataProxy.class,
        power = @Power(add = false, delete = false, edit = false)
)
@EruptDataProcessor("BarcodeList")
@Data
public class BarcodeList extends BaseModel {

    @EruptField(
            views = {
                    @View(title = "条码编号")
            },
            edit = @Edit(title = "条码编号", notNull = true, search = @Search)
    )
    private String barcodeNum;

    @EruptField(
            views = {
                    @View(title = "物料编码")
            },
            edit = @Edit(title = "物料编码", notNull = true, search = @Search)
    )
    private String stockCode;

    @EruptField(
            views = {
                    @View(title = "物料名称")
            },
            edit = @Edit(title = "物料名称", notNull = true, search = @Search)
    )
    private String stockName;

    @EruptField(
            views = {
                    @View(title = "条码类型")
            },
            edit = @Edit(title = "条码类型",
                    readonly = @Readonly(),
                    search = @Search,
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(fetchHandler = BarCodeType.Handler.class)
            )
    )

    @Convert(converter = BarCodeTypeConverter.class)
    private BarCodeType type;

    @EruptField(
            views = {
                    @View(title = "状态")
            },
            edit = @Edit(
                    title = "状态",
                    search = @Search(),
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未入库", value = STATUS_OUT + ""),
                            @VL(label = "已入库", value = STATUS_IN + ""),
                    })
            )
    )
    private Integer status;


    @EruptField(
            views = {
                    @View(title = "条码状态")
            },
            edit = @Edit(title = "条码状态",
                    search = @Search,
                    boolType = @BoolType(trueText = "生效", falseText = "失效") )
    )
    private Boolean barcodeStatus;

    @EruptField(
            views = {
                    @View(title = "数量")
            },
            edit = @Edit(title = "数量", notNull = true)
    )
    private String capacity;

    @EruptField(
            views = {
                    @View(title = "可用数量")
            },
            edit = @Edit(title = "可用数量", notNull = true)
    )
    private String availableQuantity;

    @EruptField(
            views = {
                    @View(title = "库存单位")
            },
            edit = @Edit(title = "库存单位", notNull = true)
    )
    private String stockUnitName;


    @EruptField(
            views = {
                    @View(title = "是否需要检验")
            },
            edit = @Edit(title = "是否需要检验", boolType = @BoolType(trueText = "需要", falseText = "不需要"))
    )
    private Boolean needCheck;

    @EruptField(
            views = {
                    @View(title = "检验状态")
            },
            edit = @Edit(title = "检验状态", boolType = @BoolType(trueText = "已检验", falseText = "未检验"))
    )
    private Boolean checkStatus;

    @EruptField(
            views = {
                    @View(title = "所在仓库")
            },
            edit = @Edit(title = "所在仓库", notNull = true, search = @Search(vague = true))
    )
    private String warehouseName;

    @EruptField(
            views = {
                    @View(title = "库位编码")
            },
            edit = @Edit(title = "库位编码", notNull = true, search = @Search(vague = true))
    )
    private String locationCode;

    @EruptField(
            views = {
                    @View(title = "采购订单号")
            },
            edit = @Edit(title = "采购订单号", notNull = true, search = @Search)
    )
    private String purchaseOrderCode;

    @EruptField(
            views = {
                    @View(title = "生产工单号")
            },
            edit = @Edit(title = "生产工单号", notNull = true, search = @Search)
    )
    private String workOrderCode;

    static {
        DataProcessorManager.register("BarcodeList", BarcodeListDataProcessor.class);
    }

    @Service
    public static class BarcodeListDataProcessor extends EruptDataAdapterService {

        @Resource
        private EruptUserService eruptUserService;
//        @Resource
//        private MesStockBarcodePrintDetailService printDetailService;

        private Long getTenantId() {
            return eruptUserService.getCurrentEruptUser().getTenantId();
        }

        @Data
        public static class QueryDTO {
            private int pageNum;
            private int pageSize;
            private String barcodeNum;
            private String stockName;
            private String stockCode;
            private String type;
            private String status;
            private String barcodeStatus;
            private String purchaseOrderCode;
            private String workOrderCode;
            private String warehouseName;
            private String locationCode;
            private Long tenantId;
        }

        @Override
        public Page queryList(EruptModel eruptModel, Page page, EruptQuery eruptQuery) {
            QueryDTO queryDTO = new QueryDTO();
            queryDTO.setPageNum(page.getPageIndex());
            queryDTO.setPageSize(page.getPageSize());
            queryDTO.setTenantId(getTenantId());

            Map<String, Consumer<String>> propertyMap = new HashMap<>();
            propertyMap.put("barcodeNum", queryDTO::setBarcodeNum);
            propertyMap.put("stockName", queryDTO::setStockCode);
            propertyMap.put("stockCode", queryDTO::setStockCode);
            propertyMap.put("purchaseOrderCode", queryDTO::setPurchaseOrderCode);
            propertyMap.put("workOrderCode", queryDTO::setWorkOrderCode);
            propertyMap.put("warehouseName", queryDTO::setWarehouseName);
            propertyMap.put("locationCode", queryDTO::setLocationCode);
            propertyMap.put("type", queryDTO::setType);
            propertyMap.put("status", queryDTO::setStatus);
            propertyMap.put("barcodeStatus", queryDTO::setBarcodeStatus);

            List<Condition> conditions = eruptQuery.getConditions();
            for (Condition condition : conditions) {
                String conditionKey = condition.getKey();
                String queryString = condition.getValue().toString();
                if (condition.getExpression() == EQ || condition.getExpression() == LIKE) {
                    Consumer<String> propertySetter = propertyMap.get(conditionKey);
                    if (propertySetter != null) {
                        propertySetter.accept(queryString);
                    }
                }
            }
//            com.baomidou.mybatisplus.extension.plugins.pagination.Page<BarcodeList> listPage = printDetailService.selectPage(queryDTO);
//            List<BarcodeList> records = listPage.getRecords();
//            if(CollUtil.isNotEmpty(records)){
//                List<Map<String, Object>> list = records.stream()
//                        .map(BeanUtil::beanToMap)
//                        .collect(Collectors.toList());
//                page.setList(list);
//            }else {
//                page.setList(new ArrayList<>());
//            }
//            page.setTotal(listPage.getTotal());
            return page;
        }
    }

    public static class InnerDataProxy implements DataProxy<BarcodeList> {}
}
