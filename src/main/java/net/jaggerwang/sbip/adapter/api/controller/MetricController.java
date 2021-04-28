package net.jaggerwang.sbip.adapter.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;
import net.jaggerwang.sbip.adapter.api.controller.dto.MetricDTO;

/**
 * @author Jagger Wang
 */
@RestController
@RequestMapping("/metric")
@Api(tags = "Metric Apis")
public class MetricController extends AbstractController {
    @GetMapping("/info")
    @ApiOperation("Get metric info")
    public RootDTO info() {
        var metric = metricUsecase.info();

        return new RootDTO().addDataEntry("metric", MetricDTO.fromBO(metric));
    }
}
