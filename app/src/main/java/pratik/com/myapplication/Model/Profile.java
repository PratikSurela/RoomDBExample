package pratik.com.myapplication.Model;

public class Profile {

    private String username = "", email = "", phone = "", pass = "";

    public Profile(){}
    public Profile(String username, String email, String phone, String pass) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
