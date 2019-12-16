package net.jaggerwang.sbip.usecase.port.repository;

import net.jaggerwang.sbip.entity.MetricEntity;

public interface MetricRepository {
    Long increment(String name, Long amount);

    MetricEntity get();
}
