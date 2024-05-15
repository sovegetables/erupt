package com.qamslink.mes.core;

import com.qamslink.mes.model.basic.MesBarcodeRule;
import com.qamslink.mes.model.basic.QMesBarcodeRule;
import com.qamslink.mes.repository.MesBarcodeRuleRepository;
import com.qamslink.mes.type.BarCodeRuleType;
import com.qamslink.mes.type.BarCodeType;
import com.qamslink.mes.type.TicketType;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.core.exception.EruptApiErrorTip;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderCodeGenerator implements CodeGenerator.CodeHandler {

    @Resource
    private MesBarcodeRuleRepository mesBarcodeRuleRepository;

    @Override
    public Object generateCode(Field field, Object value, List<Field> fields, CodeGenerator.KEY[] keys) {
        TicketType currentTicketType = getTicketType(keys);
        synchronized (OrderCodeGenerator.class) {
            QMesBarcodeRule mesBarcodeRule = QMesBarcodeRule.mesBarcodeRule;
            BooleanExpression booleanExpression = mesBarcodeRule.categoryType
                    .eq(BarCodeRuleType.CATEGORY_TYPE_ORDER)
                    .and(mesBarcodeRule.categoryCode.eq(currentTicketType))
                    .and(mesBarcodeRule.isUsed.eq(true));
            Optional<MesBarcodeRule> barcodeRuleOptional = mesBarcodeRuleRepository.findOne(booleanExpression);
            MesBarcodeRule rule = barcodeRuleOptional.orElse(null);
            if (rule == null) {
                throw new EruptApiErrorTip("请维护单据编号规则！");
            }
            return generateBarCode(rule, rule.getCurrentIndex());
        }
    }

    private static TicketType getTicketType(CodeGenerator.KEY[] keys) {
        if(keys == null || keys.length == 0){
            throw new IllegalArgumentException("必须要有1个param参数！");
        }
        CodeGenerator.KEY key = keys[0];
        String paramKey = key.key();
        String paramValue = key.value();
        TicketType currentTicketType = null;
        for (TicketType ticketType : TicketType.values()) {
            if(paramValue.equals(ticketType.getValueStr())){
                currentTicketType = ticketType;
                break;
            }
        }
        if(currentTicketType == null){
            throw new IllegalArgumentException("param的类型必须为TicketType!");
        }
        return currentTicketType;
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final String FORMAT_BARCODE = "%s%s%s%s";

    public String generateBarCode(BarCodeType barCodeType){
        synchronized (OrderCodeGenerator.class) {
            QMesBarcodeRule mesBarcodeRule = QMesBarcodeRule.mesBarcodeRule;
            BooleanExpression booleanExpression = mesBarcodeRule.categoryType
                    .eq(BarCodeRuleType.CATEGORY_TYPE_BAR)
                    .and(mesBarcodeRule.type.eq(barCodeType))
                    .and(mesBarcodeRule.isUsed.eq(true));
            Optional<MesBarcodeRule> barcodeRuleOptional = mesBarcodeRuleRepository.findOne(booleanExpression);
            MesBarcodeRule rule = barcodeRuleOptional.orElse(null);
            if (rule == null) {
                throw new EruptApiErrorTip("请维护单据编号规则！");
            }
            return generateBarCode(rule, rule.getCurrentIndex());
        }
    }

    private String generateBarCode(MesBarcodeRule barcodeRule, Integer currentIndex) {
        String prefix = barcodeRule.getPrefix();
        String dateFormat = DATE_FORMAT.format(new Date());
        String suffix = barcodeRule.getSuffix();
        Integer incremental = barcodeRule.getIncremental();
        Integer serialNoMin = barcodeRule.getSerialnoMin();
        Integer serialNoMax = barcodeRule.getSerialnoMax();
        Integer serialNoLength = barcodeRule.getSerialnoLength();
        int start = Math.max(currentIndex, serialNoMin);
        Integer current = start + incremental;
        StringBuilder currentCode = new StringBuilder();
        int gap = serialNoLength - String.valueOf(current).length();
        if (gap > 0) {
            for (int i = 0; i < gap; i++) {
                currentCode.append("0");
            }
        }
        currentCode.append(current);
        barcodeRule.setCurrentIndex(current);
        if (suffix == null) {
            suffix = "";
        }
        mesBarcodeRuleRepository.save(barcodeRule);
        return String.format(FORMAT_BARCODE, prefix, dateFormat.substring(2), currentCode, suffix);
    }
}
