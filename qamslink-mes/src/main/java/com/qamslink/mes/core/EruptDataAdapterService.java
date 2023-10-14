package com.qamslink.mes.core;

import xyz.erupt.core.query.Column;
import xyz.erupt.core.query.EruptQuery;
import xyz.erupt.core.service.IEruptDataService;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.core.view.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EruptDataAdapterService implements IEruptDataService {
    @Override
    public Object findDataById(EruptModel eruptModel, Object id) {
        return null;
    }

    @Override
    public Page queryList(EruptModel eruptModel, Page page, EruptQuery eruptQuery) {
        return null;
    }

    @Override
    public Collection<Map<String, Object>> queryColumn(EruptModel eruptModel, List<Column> columns, EruptQuery eruptQuery) {
        return null;
    }

    @Override
    public void addData(EruptModel eruptModel, Object object) {

    }

    @Override
    public void editData(EruptModel eruptModel, Object object) {

    }

    @Override
    public void deleteData(EruptModel eruptModel, Object object) {
    }

    @Override
    public void batchAddData(EruptModel eruptModel, List<?> objects) {
    }

    @Override
    public void batchDelete(EruptModel eruptModel, List<?> objects) {
    }
}
