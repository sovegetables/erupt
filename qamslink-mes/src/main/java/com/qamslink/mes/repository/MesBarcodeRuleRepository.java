package com.qamslink.mes.repository;

import com.qamslink.mes.model.basic.MesBarcodeRule;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesBarcodeRuleRepository extends CrudRepository<MesBarcodeRule, Long>, QuerydslPredicateExecutor<MesBarcodeRule> {
}
