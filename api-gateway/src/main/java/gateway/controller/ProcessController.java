package gateway.controller;

import gateway.dto.DocumentTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/process")
public class ProcessController {

    private final RestTemplate restTemplate;

    @Autowired
    public ProcessController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/add")
//    @HystrixCommand(fallbackMethod = "addProcessFallback")
    public ModelAndView addProcess() {
        ModelAndView mv = new ModelAndView("add_process");
        List<DocumentTypeDto> list = restTemplate.getForObject("http://descriptor-service/document-type/all", List.class);
        mv.addObject("documentTypes", list);
        return mv;
    }

    public ModelAndView addProcessFallback() {
        ModelAndView mv = new ModelAndView("add_process");
        mv.addObject("documentTypes", new ArrayList<>());
        return mv;
    }

}
