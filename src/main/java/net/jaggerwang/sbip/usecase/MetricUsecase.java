package net.jaggerwang.sbip.usecase;

import net.jaggerwang.sbip.entity.MetricEntity;
import net.jaggerwang.sbip.usecase.port.repository.MetricRepository;

public class MetricUsecase {
    private final MetricRepository metricRepository;

    public MetricUsecase(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public Long increment(String name, Long amount) {
        return metricRepository.increment(name, amount);
    }

    public MetricEntity info() {
        return metricRepository.get();
    }
}
