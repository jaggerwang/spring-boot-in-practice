package net.jaggerwang.sbip.adapter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.adapter.controller.dto.MetricDto;

@RestController
@RequestMapping("/metric")
@Api(tags = "Metric Apis")
public class MetricController extends AbstractController {
    @GetMapping("/info")
    @ApiOperation("Get metric info")
    public RootDto info() {
        var metric = metricUsecases.info();

        return new RootDto().addDataEntry("metric", MetricDto.fromEntity(metric));
    }
}
