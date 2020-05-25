package es.uji.ei1027.majorsacasa.model;

public class Company {

    private String name;
    private String cif;
    private String pwd;
    private String address;
    private String contactName;
    private String contactPhoneNumber;
    private String contactEmail;
    private String serviceType;

    public Company(){}

    public Company(Company company){
        this.name = company.name;
        this.cif = company.cif;
        this.pwd = company.pwd;
        this.address = company.address;
        this.contactName = company.contactName;
        this.contactPhoneNumber = company.contactPhoneNumber;
        this.contactEmail = company.contactEmail;
        this.serviceType = company.serviceType;

    }


    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", CIF='" + cif + '\'' +
                ", pwd='" + pwd + '\'' +
                ", address='" + address + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhoneNumber=" + contactPhoneNumber +
                ", contactEmail='" + contactEmail + '\'' +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
