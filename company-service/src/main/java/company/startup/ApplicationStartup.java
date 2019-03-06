package company.startup;

import java.util.ArrayList;
import java.util.Collections;

import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.service.CompanyService;
import company.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements InitializingBean {
    private final CompanyService companyService;
    private final UserService userService;

    @Autowired
    public ApplicationStartup(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        Company company = new Company(1, "company d.o.o", "011111111", "01111111", "Vracar");
        company = companyService.save(company);

        User admin = new User("Petar", "Petrovic", "pera", company, new ArrayList<>(Collections.singletonList(Role.ADMIN)));
        userService.save(admin);

    }
}