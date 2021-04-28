package net.jaggerwang.sbip.adapter.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.MetricBO;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricDTO {
    private Long registerCount;

    public static MetricDTO fromBO(MetricBO metricBO) {
        return MetricDTO.builder().registerCount(metricBO.getRegisterCount()).build();
    }

    public MetricBO toBO() {
        return MetricBO.builder().registerCount(registerCount).build();
    }
}
