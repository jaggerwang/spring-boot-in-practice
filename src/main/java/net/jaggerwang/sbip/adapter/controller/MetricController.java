package net.jaggerwang.sbip.adapter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.controller.dto.JsonDTO;
import net.jaggerwang.sbip.adapter.controller.dto.MetricDTO;

@RestController
@RequestMapping("/metric")
public class MetricController extends BaseController {
    @GetMapping("/info")
    public JsonDTO info() {
        var metric = metricUsecases.info();

        return new JsonDTO().addDataEntry("metric", MetricDTO.fromEntity(metric));
    }
}
