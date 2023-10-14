//package com.qamslink.mes.model.watchBoard;
//
//import cn.hutool.core.date.DateUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.qamslink.mes.entity.*;
//import com.qamslink.mes.service.MesStockService;
//import com.qamslink.mes.service.MesWorkingProcedureService;
//import com.qamslink.mes.service.production.MesProductionScheduleDetailService;
//import com.qamslink.mes.service.production.MesWorkOrderService;
//import com.qamslink.mes.service.production.MesWorkStartService;
//import com.qamslink.mes.vo.MesProductivityWatchBoardRecordVO;
//import com.qamslink.mes.vo.MesProductivityWatchBoardVO;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import xyz.erupt.upms.service.EruptUserService;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class MesWatchBoardService {
//
//    private EruptUserService eruptUserService;
//
//    private MesWorkOrderService mesWorkOrderService;
//
//    private MesProductionScheduleDetailService mesProductionScheduleDetailService;
//
//    private MesWorkStartService mesWorkStartService;
//
//    private MesStockService mesStockService;
//
//    private MesWorkingProcedureService mesWorkingProcedureService;
//
//    public MesProductivityWatchBoardVO productivityWatchBoard() {
//        List<MesWorkStartEntity> workStartAndWorkEndRecords = mesWorkStartService.list(Wrappers.<MesWorkStartEntity>lambdaQuery()
//                .isNotNull(MesWorkStartEntity::getFinishTime)
//                .isNotNull(MesWorkStartEntity::getFinishCount)
//                .orderByDesc(MesWorkStartEntity::getCreateTime)
//                .last("limit 50"));
//        List<Long> taskIds = workStartAndWorkEndRecords.stream().map(MesWorkStartEntity::getTaskId).collect(Collectors.toList());
//        List<MesProductionScheduleDetailEntity> taskList = mesProductionScheduleDetailService.listByIds(taskIds);
//        Map<Long, MesProductionScheduleDetailEntity> taskMap = taskList.stream().collect(Collectors.toMap(MesProductionScheduleDetailEntity::getId, Function.identity()));
//
//        List<Long> currentWorkingProcedureIds = taskList.stream().map(MesProductionScheduleDetailEntity::getWorkProcedureId).collect(Collectors.toList());
//        List<Long> nextTaskIds = taskList.stream().map(MesProductionScheduleDetailEntity::getNextProcedureTaskId).collect(Collectors.toList());
//        List<MesProductionScheduleDetailEntity> nextTaskList = mesProductionScheduleDetailService.listByIds(nextTaskIds);
//        Map<Long, Long> nextTaskId2WorkingProcedureIdMap = nextTaskList.stream().collect(Collectors.toMap(MesProductionScheduleDetailEntity::getId, MesProductionScheduleDetailEntity::getWorkProcedureId));
//        List<Long> nextWorkingProcedureIds = nextTaskList.stream().map(MesProductionScheduleDetailEntity::getWorkProcedureId).collect(Collectors.toList());
//        List<Long> allWorkingProcedureIds = new ArrayList<>();
//        allWorkingProcedureIds.addAll(currentWorkingProcedureIds);
//        allWorkingProcedureIds.addAll(nextWorkingProcedureIds);
//        List<MesWorkingProcedureEntity> workingProcedureEntityList = mesWorkingProcedureService.listByIds(allWorkingProcedureIds);
//        Map<Long, MesWorkingProcedureEntity> workingProcedureEntityMap = workingProcedureEntityList.stream().collect(Collectors.toMap(MesWorkingProcedureEntity::getId, Function.identity()));
//
//        List<Long> workOrderIds = workStartAndWorkEndRecords.stream().map(MesWorkStartEntity::getWorkOrderId).collect(Collectors.toList());
//        List<MesWorkOrderEntity> workOrderEntityList = mesWorkOrderService.listByIds(workOrderIds);
//        Map<Long, MesWorkOrderEntity> workOrderEntityMap = workOrderEntityList.stream().collect(Collectors.toMap(MesWorkOrderEntity::getId, Function.identity()));
//
//
//        List<Long> stockIds = workOrderEntityList.stream().map(MesWorkOrderEntity::getStockId).collect(Collectors.toList());
//        List<MesStockEntity> mesStockEntities = mesStockService.listByIds(stockIds);
//        Map<Long, MesStockEntity> stockMap = mesStockEntities.stream().collect(Collectors.toMap(MesStockEntity::getId, Function.identity()));
//
//
//        // 当日产量、当月产量、工单完成率、未报工数
//        BigDecimal todayProductionQuantity = mesWorkStartService.list(Wrappers.<MesWorkStartEntity>lambdaQuery()
//                .isNotNull(MesWorkStartEntity::getFinishCount)
//                .ge(MesWorkStartEntity::getCreateTime, DateUtil.beginOfDay(DateUtil.date()))).stream().map(MesWorkStartEntity::getFinishCount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        MesProductivityWatchBoardVO mesProductivityWatchBoardVO = new MesProductivityWatchBoardVO();
//        mesProductivityWatchBoardVO.setTodayProductionQuantity(String.format("%07d", todayProductionQuantity.intValue()));
//
//        BigDecimal tomonthProductionQuantity = mesWorkStartService.list(Wrappers.<MesWorkStartEntity>lambdaQuery()
//                .isNotNull(MesWorkStartEntity::getFinishCount)
//                .ge(MesWorkStartEntity::getCreateTime, DateUtil.beginOfMonth(DateUtil.date()))).stream().map(MesWorkStartEntity::getFinishCount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        mesProductivityWatchBoardVO.setTomonthProductionQuantity(String.format("%07d", tomonthProductionQuantity.intValue()));
//
//        long totalQuantity = mesWorkOrderService.count();
//        long finishedQuantity = mesWorkOrderService.count(Wrappers.<MesWorkOrderEntity>lambdaQuery()
//                .eq(MesWorkOrderEntity::getStatus, 3));
//        BigDecimal finishedRate = BigDecimal.valueOf(totalQuantity).divide(BigDecimal.valueOf(finishedQuantity), 2, RoundingMode.HALF_UP);
//        mesProductivityWatchBoardVO.setWorkOrderFinishedRate(finishedRate);
//
//        List<MesProductionScheduleDetailEntity> unFinishedTaskList = mesProductionScheduleDetailService.list(Wrappers.<MesProductionScheduleDetailEntity>lambdaQuery()
//                .last("where finish_num < num"));
//        BigDecimal unFinishedQuantity = unFinishedTaskList.stream().map(i -> i.getNum().subtract(i.getFinishNum())).reduce(BigDecimal.ZERO, BigDecimal::add);
//        mesProductivityWatchBoardVO.setUnFinishQuantity(String.format("%07d", unFinishedQuantity.intValue()));
//
//        List<MesProductivityWatchBoardRecordVO> mesProductivityWatchBoardRecordVOList = new ArrayList<>();
//        for (MesWorkStartEntity workStartAndWorkEndRecord : workStartAndWorkEndRecords) {
//            MesProductionScheduleDetailEntity task = taskMap.get(workStartAndWorkEndRecord.getTaskId());
//            MesWorkOrderEntity mesWorkOrderEntity = workOrderEntityMap.get(workStartAndWorkEndRecord.getWorkOrderId());
//            MesStockEntity mesStockEntity = stockMap.get(mesWorkOrderEntity.getStockId());
//            if (task == null || mesStockEntity == null) {
//                continue;
//            }
//            MesProductivityWatchBoardRecordVO record = new MesProductivityWatchBoardRecordVO();
//            record.setTaskCode(task.getCode());
//            record.setProductionOrderCode(mesWorkOrderEntity.getOrderCode());
//            record.setProductionName(mesStockEntity.getName());
//            record.setPlanNum(task.getNum());
//            record.setFinishedNum(task.getFinishNum());
//            MesWorkingProcedureEntity currentWorkingProcedure = workingProcedureEntityMap.get(task.getWorkProcedureId());
//            MesWorkingProcedureEntity nextWorkingProcedure = workingProcedureEntityMap.get(nextTaskId2WorkingProcedureIdMap.get(task.getNextProcedureTaskId()));
//            record.setCurrentWorkingProduceName(currentWorkingProcedure.getName());
//            record.setNextWorkingProduceName(nextWorkingProcedure != null ? nextWorkingProcedure.getName() : "");
//            record.setPlanStartTime(task.getStartDate());
//            record.setPlanEndTime(task.getEndDate());
//            record.setActualStartTime(task.getActualStartDate());
//            record.setActualEndTime(task.getActualEndDate());
//            record.setQualifiedQuantity(task.getFinishNum().subtract(task.getScrapNum()));
//            record.setScrapQuantity(String.valueOf(task.getScrapNum().longValue()));
//            record.setFinishedRate(task.getFinishNum().divide(task.getNum(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100L)));
//            mesProductivityWatchBoardRecordVOList.add(record);
//        }
//        mesProductivityWatchBoardVO.setRecords(mesProductivityWatchBoardRecordVOList);
//        return mesProductivityWatchBoardVO;
//    }
//
//}
