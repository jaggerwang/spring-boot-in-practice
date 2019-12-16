package net.jaggerwang.sbip.adapter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.controller.dto.JsonDto;
import net.jaggerwang.sbip.adapter.controller.dto.MetricDto;

@RestController
@RequestMapping("/metric")
public class MetricController extends BaseController {
    @GetMapping("/info")
    public JsonDto info() {
        var metric = metricUsecases.info();

        return new JsonDto().addDataEntry("metric", MetricDto.fromEntity(metric));
    }
}
