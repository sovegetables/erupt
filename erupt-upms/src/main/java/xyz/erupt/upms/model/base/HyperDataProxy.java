package xyz.erupt.upms.model.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.core.exception.EruptApiErrorTip;
import xyz.erupt.core.service.EruptCoreService;
import xyz.erupt.core.util.EruptSpringUtil;
import xyz.erupt.core.view.EruptFieldModel;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.model.EruptUserVo;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static xyz.erupt.core.util.Erupts.generateCode;

/**
 * @author YuePeng
 * date 2020-08-04
 */
@Service
@Slf4j
public class HyperDataProxy implements DataProxy<HyperModel> {

    @Resource
    private EruptUserService eruptUserService;

    @Override
    public void beforeAdd(HyperModel hyperModel) {
        setGeneratorCode(hyperModel);
        hyperModel.setCreateTime(new Date());
        hyperModel.setCreateUser(new EruptUserVo(eruptUserService.getCurrentUid()));
        hyperModel.setUpdateTime(new Date());
        hyperModel.setUpdateUser(new EruptUserVo(eruptUserService.getCurrentUid()));
    }

    private static void setGeneratorCode(HyperModel hyperModel) {
        Class<? extends BaseModel> hyperModelClass = hyperModel.getClass();
        String eruptName = hyperModelClass.getSimpleName();
        EruptModel eruptModel = EruptCoreService.getErupt(eruptName);
        List<EruptFieldModel> fieldModels = eruptModel.getEruptFieldModels();
        List<Field> fields = fieldModels.stream().map(EruptFieldModel::getField).collect(Collectors.toList());
        Map<Field, CodeGenerator> fieldMap = fieldModels
                .stream().map(EruptFieldModel::getField).filter(field -> {
                    CodeGenerator annotation = field.getAnnotation(CodeGenerator.class);
                    return annotation != null;
                })
                .collect(Collectors.toMap(Function.identity(),
                        field -> field.getAnnotation(CodeGenerator.class)));
        for (Field field : fieldMap.keySet()) {
            try {
                Object value = field.get(hyperModel);
                if(value == null || (value instanceof CharSequence && StringUtils.isBlank((CharSequence) value))){
                    CodeGenerator codeGenerator = fieldMap.get(field);
                    Class<? extends CodeGenerator.CodeHandler> handler = codeGenerator.handler();
                    CodeGenerator.KEY[] params = codeGenerator.params();
                    if(handler != CodeGenerator.NOT.class){
                        CodeGenerator.CodeHandler codeHandler = EruptSpringUtil.getBean(handler);
                        if(codeHandler == null){
                            codeHandler = handler.newInstance();
                        }
                        field.set(hyperModel, codeHandler.generateCode(field, hyperModel, fields, params));
                    }else {
                        field.set(hyperModel, generateCode(8));
                    }
                }
            } catch (Exception e) {
                HyperDataProxy.log.error("CodeGenerator error:", e);
                throw new EruptApiErrorTip(e.getMessage());
            }
        }
    }

    @Override
    public void beforeUpdate(HyperModel hyperModel) {
        setGeneratorCode(hyperModel);
        hyperModel.setUpdateTime(new Date());
        hyperModel.setUpdateUser(new EruptUserVo(eruptUserService.getCurrentUid()));
    }
}
