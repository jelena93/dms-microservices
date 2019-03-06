package company.command;

import java.util.List;

import company.domain.Role;

public class UserCmd {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Long companyId;
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserCmd{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\''
                + ", surname='" + surname + '\'' + ", companyId=" + companyId + ", roles=" + roles + '}';
    }
}
