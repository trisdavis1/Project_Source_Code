public class Client {

    private int number;
    private String first_name;
    private String last_name;
    private String company;
    private String address;
    private String city;
    private String county;
    private String state;
    private int postal_code;
    private double risk_factor;
    private String phone1;
    private String phon2;
    private String email;
    private String web;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getF_name() {
        return first_name;
    }

    public void setF_name(String f_name) {
        this.first_name = f_name;
    }

    public String getL_name() {
        return last_name;
    }

    public void setL_name(String l_name) {
        this.last_name = l_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public double getRisk_factor() {
        return risk_factor;
    }

    public void setRisk_factor(double risk_factor) {
        this.risk_factor = risk_factor;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phon2;
    }

    public void setPhone2(String phon2) {
        this.phon2 = phon2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "\"client\": {" +
                "\"number\":" + "\"" + number + "\"" +
                ", \"first_name\":" + "\"" + first_name + "\"" +
                ", \"last_name\":" + "\"" + last_name + "\"" +
                ", \"company\":" + "\"" + company + "\"" +
                ", \"address\":" + "\"" + address + "\"" +
                ", \"city\":" + "\"" + city + "\"" +
                ", \"county\":" + "\"" + county + "\"" +
                ", \"state\":" + "\"" + state + "\"" +
                ", \"postal_code\":" + "\"" + postal_code + "\"" +
                ", \"risk_factor\":" + "\"" + risk_factor + "\"" +
                ", \"phone1\":" + "\"" + phone1 + "\"" +
                ", \"phon2\":" + "\"" + phon2 + "\"" +
                ", \"email\":" + "\"" + email + "\"" +
                ", \"web\":" + "\"" + web + "\"" +
                "}";
    }
}//end of class Client
