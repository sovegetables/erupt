package com.qamslink.mes.repository;

import com.qamslink.mes.model.production.PurOrderStock;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesPurOrderStockRepository extends CrudRepository<PurOrderStock, Long>, QuerydslPredicateExecutor<PurOrderStock> {
}
