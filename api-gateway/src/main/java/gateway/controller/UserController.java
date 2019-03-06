package gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final RestTemplate restTemplate;

    @Autowired
    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/add")
//    @HystrixCommand(fallbackMethod = "addUserFallback")
    public ModelAndView addUser() {
        ModelAndView mv = new ModelAndView("add_user");
        mv.addObject("roles", restTemplate.getForObject("http://company-service/roles", List.class));
        mv.addObject("companies", restTemplate.getForObject("http://company-service/all", List.class));
        return mv;
    }

    @GetMapping(path = "/search")
    public String searchUsers() {
        return "search_users";
    }

    public ModelAndView addUserFallback() {
        ModelAndView mv = new ModelAndView("add_user");
        mv.addObject("roles", Arrays.asList(new String[]{"ADMIN", "USER", "UPLOADER"}));
        mv.addObject("companies", new ArrayList());
        return mv;
    }
}
