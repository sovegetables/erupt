package com.qamslink.mes.model.basic;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mes_unit_measure_code_detail", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Setter
@Getter
@Erupt(name = "计量单位转换",
        orderBy = "MesUnitMeasureCodeDetail.createTime desc",
        power = @Power(importable = true)
        )
@Slf4j
public class MesUnitMeasureCodeDetail extends HyperModelVo {

    public static class InnerCodeGenerator implements CodeGenerator.CodeHandler{

        @Override
        public Object generateCode(Field field, Object value, List<Field> fields, CodeGenerator.KEY[] keys) {
            for (Field f : fields) {
                if(f.getName().equals("stock")){
                    try {
                        MesStock o = (MesStock) f.get(value);
                        return o.getCode() + "_Unit";
                    } catch (IllegalAccessException e) {
                        log.error("InnerCodeGenerator:", e);
                    }
                }
            }
            return null;
        }
    }

    @EruptField(
            views = @View(title = "编码", highlight = true),
            edit = @Edit(title = "编码", notNull = true, show = false)
    )
    @CodeGenerator()
    private String code;

//    @ManyToOne
//    @EruptField(
//            views ={@View(title = "物料编码",column = "code")},
//            edit = @Edit(title = "物料编码",notNull = true, search = @Search(vague = true),
//                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
//    )
//    private MesStock stock;

    @ManyToOne
    @EruptField(
            views ={@View(title = "单位",column = "name"),
                    @View(title = "单位编码",column = "unitCode")},
            edit = @Edit(title = "单位", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private Unit beforeUnitMeasureCode;

    @ManyToOne
    @EruptField(
            views ={@View(title = "转换单位",column = "name"),
                    @View(title = "转换单位编码",column = "unitCode")},
            edit = @Edit(title = "转换单位",notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private Unit afterUnitMeasureCode;

    @EruptField(
            views = @View(title = "转换率"),
            edit = @Edit(title = "转换率", notNull = true, type = EditType.NUMBER)
    )
    private BigDecimal conversionRate;
}
