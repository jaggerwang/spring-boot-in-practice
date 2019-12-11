package net.jaggerwang.sbip.usecase.port.repository;

import net.jaggerwang.sbip.entity.MetricEntity;

public interface MetricRepository {
    long increment(String name, long amount);

    MetricEntity get();
}
