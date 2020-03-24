package net.jaggerwang.sbip.adapter.controller;

import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController extends AbstractController {
    @GetMapping("/")
    public RootDto index() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return new RootDto().addDataEntry("authentication", authentication);
    }
}
