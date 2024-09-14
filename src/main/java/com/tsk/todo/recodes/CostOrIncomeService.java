package com.tsk.todo.recodes;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.budgets.BudgetService;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.BudgetsMapper;
import com.tsk.todo.mapper.RecordsMapper;
import com.tsk.todo.pojo.BudgetsPojo;
import com.tsk.todo.pojo.CategoriesPojo;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.req.AddCostOrIncomeReq;
import com.tsk.todo.util.AuthUtils;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Service
public class CostOrIncomeService extends ServiceImpl<RecordsMapper, RecordsPojo> implements ICostOrIncomeService {

    @Resource
    private BudgetsMapper budgetsMapper;

    @Resource
    private RecordsMapper recordsMapper;

    public ResultResponse<String> add(AddCostOrIncomeReq addCostOrIncomeReq) {
        RecordsPojo recordsPojo = new RecordsPojo();
        recordsPojo.setIncomeOrExpand(addCostOrIncomeReq.getIncomeOrExpand());
        recordsPojo.setMoney(addCostOrIncomeReq.getMoney());
        recordsPojo.setPayAccount(addCostOrIncomeReq.getPayAccount());
        recordsPojo.setRemark(addCostOrIncomeReq.getRemark());
        recordsPojo.setPicUrls(addCostOrIncomeReq.getPicUrls());

        recordsPojo.setCategoryId(addCostOrIncomeReq.getCategoryId());
        // 转换成时间戳
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = addCostOrIncomeReq.getTime();
        long epochMilli = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        recordsPojo.setTime(epochMilli);
        recordsPojo.setUserId(AuthUtils.getLoginUserId());
        this.save(recordsPojo);
        return ResultResponse.success("添加成功");
    }

    private Long formatStrToTimestamp(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate localDate = LocalDate.parse(dateString);
        // 将LocalDate转换为Instant，再转换为时间戳
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public Map<String, Float> getCostAndIncome(long startTimestamp, long endTimestamp) {
        // 计算在这个时间戳之内的消费和支出
        HashMap<String, Float> map = new HashMap<>();
        map.put("cost", 0f);
        map.put("income", 1f);
        map.put("budgetCount", 0f);

        LambdaQueryWrapper<RecordsPojo> recordsPojoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recordsPojoLambdaQueryWrapper.between(RecordsPojo::getTime, startTimestamp, endTimestamp);
        List<RecordsPojo> recordsPojos = list(recordsPojoLambdaQueryWrapper);
// 消费支出记录
        for (RecordsPojo recordsPojo : recordsPojos) {
            if (recordsPojo.getIncomeOrExpand() == 0) {
                map.put("cost", map.get("cost") + recordsPojo.getMoney());
            } else {
                map.put("income", map.get("income") + recordsPojo.getMoney());
            }
        }
//        预算
        LambdaQueryWrapper<BudgetsPojo> budgetsPojoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        budgetsPojoLambdaQueryWrapper.ge(BudgetsPojo::getStartTime, startTimestamp);
        budgetsPojoLambdaQueryWrapper.le(BudgetsPojo::getEndTime, endTimestamp);
        List<BudgetsPojo> budgetsPojos = budgetsMapper.selectList(budgetsPojoLambdaQueryWrapper);
        for (BudgetsPojo budgetsPojo : budgetsPojos) {
            map.put("budgetCount", map.get("budgetCount") + budgetsPojo.getCount());
        }
        return map;
    }

    public ResultResponse<List<RecordsPojo>> getAllRecords(String incomeOrCost, String startTime, String endTime) {
        long startTimeLong = this.formatStrToTimestamp(startTime);
        long oneDayTimestamp = 24 * 60 * 60 * 1000;
        // 需要延长一天
        long endTimeLong = this.formatStrToTimestamp(endTime) + oneDayTimestamp;
        // 寻找出time 在 startTime 和 endTime 之间 并且 incomeOrCost 与 type 相同
        LambdaQueryWrapper<RecordsPojo> recordsPojoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        recordsPojoLambdaQueryWrapper.between(StrUtil.isNotBlank(startTime) && StrUtil.isNotBlank(endTime), RecordsPojo::getTime, startTimeLong, endTimeLong);
        recordsPojoLambdaQueryWrapper.eq(StrUtil.isNotBlank(incomeOrCost), RecordsPojo::getIncomeOrExpand, incomeOrCost);
        List<RecordsPojo> list = list(recordsPojoLambdaQueryWrapper);
        return ResultResponse.success(list);
    }

    public ResultResponse<Map<String, Double>> getAllRecordsSeparate() {

        Integer loginUserId = AuthUtils.getLoginUserId();
        List<RecordsPojo> recordsPojoList = recordsMapper.selectList(new LambdaQueryWrapper<RecordsPojo>().eq(RecordsPojo::getUserId, loginUserId));

        Map<String, Double> summary = new HashMap<>();
        // 分别计算今天、本周、本月的收入和支出
        calculateSummary(recordsPojoList, this::isInToday, summary, "inToday");
        calculateSummary(recordsPojoList, this::isInCurrentWeek, summary, "inWeek");
        calculateSummary(recordsPojoList, this::isInCurrentMonth, summary, "inMonth");
        return ResultResponse.success(summary);
    }

    public ResultResponse<RecordsPojo> getRecordById(String recordId) {
        RecordsPojo one = this.getById(recordId);
        return ResultResponse.success(one);
    }

    private void calculateSummary(List<RecordsPojo> records,
                                  java.util.function.Predicate<Long> condition,
                                  Map<String, Double> summary, String prefix) {

        Map<Integer, Double> groupedByType = records.stream()
                .filter(record -> condition.test(record.getTime()))
                .collect(Collectors.groupingBy(
                        RecordsPojo::getIncomeOrExpand,
                        Collectors.summingDouble(RecordsPojo::getMoney)
                ));

        summary.put(prefix + "Cost", groupedByType.getOrDefault(1, 0.0));
        summary.put(prefix + "Income", groupedByType.getOrDefault(0, 0.0));
    }

    private boolean isInCurrentWeek(long timestamp) {
        LocalDate dateFromTimestamp = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate startOfWeek = LocalDate.now(ZoneOffset.UTC).with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return !dateFromTimestamp.isBefore(startOfWeek) && !dateFromTimestamp.isAfter(endOfWeek);
    }

    private boolean isInToday(long timestamp) {
        LocalDate dateFromTimestamp = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        return dateFromTimestamp.equals(today);
    }

    private boolean isInCurrentMonth(long timestamp) {
        LocalDate dateFromTimestamp = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        return dateFromTimestamp.getMonth() == today.getMonth() && dateFromTimestamp.getYear() == today.getYear();
    }


}
