package company.command;

public class CompanyCmd {
    private String name;
    private String pib;
    private String identificationNumber;
    private String headquarters;

    public String getName() {
        return name;
    }

    public String getPib() {
        return pib;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    @Override
    public String toString() {
        return "CompanyCmd{" +
                "name='" + name + '\'' +
                ", pib='" + pib + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", headquarters='" + headquarters + '\'' +
                '}';
    }
}
