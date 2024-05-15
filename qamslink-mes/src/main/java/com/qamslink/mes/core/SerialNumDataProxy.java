package com.qamslink.mes.core;

import xyz.erupt.annotation.fun.DataProxy;

import java.util.Collection;
import java.util.Map;

public class SerialNumDataProxy implements DataProxy<SerialNumModel> {
    @Override
    public void addBehavior(SerialNumModel serialNumModel) {
        /* TODO: 11/14/23 */
    }

    @Override
    public void afterFetch(Collection<Map<String, Object>> list) {
        DataProxy.super.afterFetch(list);
    }
}
