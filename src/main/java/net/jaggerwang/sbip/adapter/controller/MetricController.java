package net.jaggerwang.sbip.adapter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.adapter.controller.dto.MetricDto;

@RestController
@RequestMapping("/metric")
public class MetricController extends AbstractController {
    @GetMapping("/info")
    public RootDto info() {
        var metric = metricUsecases.info();

        return new RootDto().addDataEntry("metric", MetricDto.fromEntity(metric));
    }
}
