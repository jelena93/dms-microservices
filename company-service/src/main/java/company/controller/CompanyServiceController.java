package company.controller;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import company.command.CompanyCmd;
import company.command.UserCmd;
import company.domain.Company;
import company.domain.Role;
import company.domain.User;
import company.dto.CompanyDto;
import company.dto.UserDto;
import company.mapper.CompanyMapper;
import company.mapper.UserMapper;
import company.service.CompanyService;
import company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class CompanyServiceController {

    private final CompanyService companyService;
    private final UserService userService;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    @Autowired
    public CompanyServiceController(CompanyService companyService, UserService userService,
                                    CompanyMapper companyMapper, UserMapper userMapper) {
        this.companyService = companyService;
        this.userService = userService;
        this.companyMapper = companyMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/all")
    public List<CompanyDto> getCompanies() {
        return companyMapper.mapToModelList(companyService.findAll());
    }

    @GetMapping("/search")
    public List<CompanyDto> search(String query) {
        if (query == null || query.isEmpty()) {
            return companyMapper.mapToModelList(companyService.findAll());
        }
        return companyMapper.mapToModelList(companyService.search(query));
    }

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable long id) {
        Company company = companyService.findOne(id);
        return companyMapper.mapToModel(company);
    }

    @PostMapping
    public CompanyDto addCompany(@RequestBody @Valid CompanyCmd companyCmd) throws Exception {
        System.out.println("addCompany " + companyCmd);
        return companyMapper.mapToModel(companyService.save(companyMapper.mapToEntity(companyCmd)));
    }

    @PutMapping("/{id}")
    public CompanyDto editCompany(@PathVariable long id, @RequestBody @Valid CompanyCmd companyCmd) throws Exception {
        Company company = companyService.findOne(id);
        if (company == null) throw new Exception("There is no company with id " + id);
        System.out.println("editCompany " + companyCmd);
        companyMapper.updateEntityFromModel(companyCmd, company);
        return companyMapper.mapToModel(companyService.save(company));
    }

    @GetMapping("/user/{username}")
    public UserDto getUser(@PathVariable String username, Principal principal) throws Exception {
        UserDto userDto = userMapper.mapToModel(userService.findOne(username));
        if (!principal.getName().equals(userDto.getUsername())) {
            throw new Exception("Not allowed to see other users");
        }
        System.out.println(userDto);
        return userDto;
    }

    @GetMapping("/user/search")
    public List<UserDto> searchUsers(String query) {
        if (query == null || query.isEmpty()) return userMapper.mapToModelList(userService.findAll());
        return userMapper.mapToModelList(userService.search(query));
    }

    @PostMapping("/user")
    public UserDto addUser(@RequestBody @Valid UserCmd userCmd) throws Exception {
        System.out.println("addUserToCompany " + userCmd);
        User user = userService.save(userMapper.mapToEntity(userCmd));
        return userMapper.mapToModel(user);
    }

    @GetMapping("/roles")
    public Role[] getRoles() {
        return Role.values();
    }

}
