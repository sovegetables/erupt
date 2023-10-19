package xyz.erupt.upms.model.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.core.service.EruptCoreService;
import xyz.erupt.core.view.EruptFieldModel;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.model.EruptUserVo;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
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
        List<Field> fields = eruptModel.getEruptFieldModels()
                .stream().map(EruptFieldModel::getField).filter(field -> field
                        .getAnnotation(CodeGenerator.class) != null)
                .collect(Collectors.toList());
        for (Field field : fields) {
            try {
                Object value = field.get(hyperModel);
                if(value == null || (value instanceof CharSequence && StringUtils.isBlank((CharSequence) value))){
                    field.set(hyperModel, generateCode(8));
                }
            } catch (IllegalAccessException e) {
                HyperDataProxy.log.error("CodeGenerator error:", e);
            }
        }
    }

    @Override
    public void beforeUpdate(HyperModel hyperModel) {
        hyperModel.setUpdateTime(new Date());
        hyperModel.setUpdateUser(new EruptUserVo(eruptUserService.getCurrentUid()));
    }
}
