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

    public static MetricDTO fromEntity(MetricBO userEntity) {
        return MetricDTO.builder().registerCount(userEntity.getRegisterCount()).build();
    }

    public MetricBO toEntity() {
        return MetricBO.builder().registerCount(registerCount).build();
    }
}
