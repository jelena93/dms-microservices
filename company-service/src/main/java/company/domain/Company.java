package company.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = -5946299712984511388L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "pib", unique = true)
    @NotNull
    @NotEmpty
    private String pib;

    @Column(name = "identification_number", unique = true)
    @NotNull
    @NotEmpty
    private String identificationNumber;

    @Column(name = "headquarters")
    @NotNull
    @NotEmpty
    private String headquarters;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
    private List<User> userList = new ArrayList<>();

    public Company() {
    }

    public Company(long id, String name, String pib, String identificationNumber, String headquarters) {
        this.id = id;
        this.name = name;
        this.pib = pib;
        this.identificationNumber = identificationNumber;
        this.headquarters = headquarters;
        this.userList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Company{" + "id=" + id + ", name='" + name + '\'' + ", pib='" + pib + '\'' + ", identificationNumber='"
                + identificationNumber + '\'' + ", headquarters='" + headquarters + '\'' + ", userList=" + userList
                + '}';
    }
}
