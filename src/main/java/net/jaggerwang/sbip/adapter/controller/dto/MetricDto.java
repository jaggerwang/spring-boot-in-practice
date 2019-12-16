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
public class MetricDto {
    private Long registerCount;

    public static MetricDto fromEntity(MetricEntity userEntity) {
        return MetricDto.builder().registerCount(userEntity.getRegisterCount()).build();
    }

    public MetricEntity toEntity() {
        return MetricEntity.builder().registerCount(registerCount).build();
    }
}
