package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DashboardController {

    @GetMapping
    public ModelAndView dashboard(/*Authentication authentication*/) {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
//        model.addObject("user", authentication);
        return model;
    }
}
