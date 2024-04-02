package ua.vholovetskyi.model;

public class Customer {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String city;
    private String zipCod;

    public Customer(String email, String firstName, String lastName, String phone, String street, String city, String zipCod) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.zipCod = zipCod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCod() {
        return zipCod;
    }

    public void setZipCod(String zipCod) {
        this.zipCod = zipCod;
    }
}
