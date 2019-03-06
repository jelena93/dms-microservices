package gateway.controller;

import gateway.dto.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final RestTemplate restTemplate;

    @Autowired
    public CompanyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/add")
    public String addCompany() {
        return "add_company";
    }

    @GetMapping(path = "/search")
    public String searchCompanies() {
        return "search_companies";
    }

    @GetMapping(path = "/{companyId}")
//    @HystrixCommand(fallbackMethod = "showCompanyFallback")
    public ModelAndView showCompany(@PathVariable long companyId) {
        ModelAndView mv = new ModelAndView("company");
        CompanyDto companyDto = restTemplate.getForObject("http://company-service/" + companyId, CompanyDto.class);
        if (companyDto == null) {
            return new ModelAndView("redirect:/company/search");
        }
        mv.addObject("company", companyDto);
        return mv;
    }

    public ModelAndView showCompanyFallback(@PathVariable long companyId) {
        ModelAndView mv = new ModelAndView("redirect:/company/search");
        return mv;
    }
}
