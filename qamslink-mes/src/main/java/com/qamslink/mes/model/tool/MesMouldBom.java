package com.qamslink.mes.model.tool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.exception.EruptApiErrorTip;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.excel.service.EruptExcelService;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "mes_mould_bom")
@Setter
@Getter
@Erupt(name = "工具清单",
//        dataProxy = MesMouldBomService.class,
        orderBy = "MesMouldBom.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesMouldBom.tenantId",
                params = {"and MesMouldBom.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_mould_bom set deleted = true where id = ?")
public class MesMouldBom extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "工具BOM名称"),
            edit = @Edit(title = "工具BOM名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "工具BOM编码"),
            edit = @Edit(title = "工具BOM编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "构件", column = "name"),
            edit = @Edit(title = "构件", type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesMouldComponent mouldComponent;

    @EruptField(
            views = @View(title = "内径长（cm）"),
            edit = @Edit(title = "内径长（cm）", numberType = @NumberType(min = 0))
    )
    private BigDecimal internalDiameter;

    @EruptField(
            views = @View(title = "外径长（cm）"),
            edit = @Edit(title = "外径长（cm）", numberType = @NumberType(min = 0))
    )
    private BigDecimal externalDiameter;

    @EruptField(
            views = @View(title = "面积（cm²）"),
            edit = @Edit(title = "面积（cm²）", numberType = @NumberType(min = 0))
    )
    private BigDecimal area;

    @EruptField(
            views = @View(title = "最大压力（Ton）"),
            edit = @Edit(title = "最大压力（Ton）", numberType = @NumberType(min = 0))
    )
    private BigDecimal maxPressure;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @JoinColumn(name = "mould_bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "构件"),
            edit = @Edit(title = "构件", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesMouldBomComponent> mouldComponents;
    private Boolean deleted = false;
}
