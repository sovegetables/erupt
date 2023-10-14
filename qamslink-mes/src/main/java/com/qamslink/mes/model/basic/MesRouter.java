package com.qamslink.mes.model.basic;


import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.OperationHandler;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "mes_router")
@Erupt(name = "路由管理",
//        dataProxy = MesRouterService.class,
        rowOperation = @RowOperation(
                code = "router",
                icon = "",
                title = "设计工艺路线",
//                type = RowOperation.Type.TPL,
//                mode = RowOperation.Mode.BUTTON,
//                tpl = @Tpl(
//                        path = "/tpl/index.html",
//                        tplHandler = MesRouter.class,
//                        engine = Tpl.Engine.Native
//                ),
                operationHandler = MesRouter.OperationHandlerImpl.class
        ),

        filter = @Filter(value = "MesRouter.tenantId", params = {"and MesRouter.deleted = false"}, conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_router set deleted = true where id = ?")
public class MesRouter extends HyperModelCreatorVo implements Tpl.TplHandler{
    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "编码"),
            edit = @Edit(title = "编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", notNull = true, search = @Search(vague = true),
                    boolType = @BoolType(trueText = "启用", falseText = "停用"))
    )
    private Boolean status = true;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;

    @Override
    public void bindTplData(Map<String, Object> binding, String[] params) {
        System.out.println("bindTplData!");
    }

    public static class OperationHandlerImpl implements OperationHandler {

        @Override
        public String exec(List data, Object o, String[] param) {
            System.out.println("exec!");
            return null;
        }
    }
}
