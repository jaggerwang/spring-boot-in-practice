package net.jaggerwang.sbip.adapter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.MetricEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricDTO {
    private long registerCount;

    public static MetricDTO fromEntity(MetricEntity userEntity) {
        return MetricDTO.builder().registerCount(userEntity.getRegisterCount()).build();
    }

    public MetricEntity toEntity() {
        return MetricEntity.builder().registerCount(registerCount).build();
    }
}
