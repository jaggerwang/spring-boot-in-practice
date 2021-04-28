package net.jaggerwang.sbip.usecase;

import net.jaggerwang.sbip.entity.MetricBO;
import net.jaggerwang.sbip.usecase.port.dao.MetricDAO;
import org.springframework.stereotype.Component;

/**
 * @author Jagger Wang
 */
@Component
public class MetricUsecase {
    private final MetricDAO metricDAO;

    public MetricUsecase(MetricDAO metricDAO) {
        this.metricDAO = metricDAO;
    }

    public Long increment(String name, Long amount) {
        return metricDAO.increment(name, amount);
    }

    public MetricBO info() {
        return metricDAO.get();
    }
}
