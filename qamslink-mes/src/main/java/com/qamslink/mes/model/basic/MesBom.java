package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Table(name="mes_bom", uniqueConstraints={ @UniqueConstraint(columnNames = {"code"})})
@Erupt(name = "物料清单",
//        dataProxy = MesBomService.class,
        orderBy = "MesBom.createTime desc")
@Slf4j
public class MesBom extends HyperModelVo {

    public static class InnerCodeGenerator implements CodeGenerator.CodeHandler{
        @Override
        public Object generateCode(Field field, Object value, List<Field> fields) {
            Map<String, Field> fieldMap = fields.stream()
                    .collect(Collectors.toMap(Field::getName, Function.identity()));
            try {
                Field stock = fieldMap.get("mainStock");
                MesStock o = (MesStock) stock.get(value);
                Field versionField = fieldMap.get("version");
                String version = (String) versionField.get(value);
                return o.getCode() + "_BOM_" + version;
            } catch (IllegalAccessException e) {
                log.error("InnerCodeGenerator:", e);
            }
            return null;
        }
    }

    @EruptField(
            views = @View(title = "物料清单编码", highlight = true),
            edit = @Edit(title = "物料清单编码",
                    notNull = true,
                    placeHolder = "保存时自动生成",
                    search = @Search(vague = true))
    )
    @CodeGenerator(handler = InnerCodeGenerator.class)
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code"),
                    @View(title = "物料名称", column = "name")
            },
            edit = @Edit(title = "物料编码", type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"),
                    notNull = true, search = @Search(vague = true))
    )
    private MesStock mainStock;

    @EruptField(
            views = @View(title = "是否默认BOM"),
            edit = @Edit(title = "是否默认BOM", boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean isDefault = true;

    @EruptField(
            views = @View(title = "版本号"),
            edit = @Edit(title = "版本号")
    )
    private String version;

    @JoinColumn(name = "bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesBomStock> stocks;

//    @JoinColumn(name = "bom_id")
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @EruptField(
//            views = @View(title = "工序"),
//            edit = @Edit(title = "工序", type = EditType.TAB_TABLE_ADD)
//    )
//    private List<MesBomWorkingProcedure> bomWorkingProcedures;
//    private Boolean deleted = false;
}
