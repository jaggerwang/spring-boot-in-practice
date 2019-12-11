package net.jaggerwang.sbip.usecase;

import net.jaggerwang.sbip.entity.MetricEntity;
import net.jaggerwang.sbip.usecase.port.repository.MetricRepository;

public class MetricUsecases extends BaseUsecases {
    private MetricRepository metricRepository;

    public MetricUsecases(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public long increment(String name, long amount) {
        return metricRepository.increment(name, amount);
    }

    public MetricEntity info() {
        return metricRepository.get();
    }
}
