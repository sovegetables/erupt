package com.qamslink.mes.gson;

import com.google.gson.GsonBuilder;
import xyz.erupt.upms.converter.AbEnumConverter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import xyz.erupt.core.config.GsonFactory;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

@Component
@Slf4j
public class GsonConfig {

    /**
     * init Gson AbEnumConverter implemention JsonDeserializers
     * EruptModifyController.updateEruptData method:
     * Object o = GsonFactory.getGsonBuilder().create().fromJson(data.toString(), eruptModel.getClazz());
     */
    static {
        GsonBuilder gsonBuilder = GsonFactory.getGsonBuilder();
        Reflections reflections = new Reflections("com.qamslink.mes.converter");
        Set<Class<? extends AbEnumConverter>> classes = reflections.getSubTypesOf(AbEnumConverter.class);
        for(Class clazz : classes) {
            if(!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())){
                ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
                Type actualType = parameterizedType.getActualTypeArguments()[0];
                try {
                    gsonBuilder.registerTypeAdapter(actualType, clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("init gson config:", e);
                }
            }
        }
    }

}
