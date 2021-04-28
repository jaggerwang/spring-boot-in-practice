package net.jaggerwang.sbip.adapter.api.controller;

import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jagger Wang
 */
@RestController
@RequestMapping("/")
public class IndexController extends AbstractController {
    @GetMapping("/")
    public RootDTO index() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return new RootDTO().addDataEntry("authentication", authentication);
    }
}
