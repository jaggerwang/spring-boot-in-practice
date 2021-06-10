package net.jaggerwang.sbip.usecase.port.dao;

import net.jaggerwang.sbip.entity.MetricBO;

/**
 * @author Jagger Wang
 */
public interface MetricDao {
    /**
     * 更新某个指标的值
     * @param name 指标名
     * @param amount 增量，可以为负数
     * @return 更新后的值
     */
    Long increment(String name, Long amount);

    /**
     * 查询所有指标的当前值
     * @return 所有指标及其当前值
     */
    MetricBO get();
}
