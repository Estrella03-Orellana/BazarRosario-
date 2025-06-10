package dominio;

public class User {
    private int id;
    private String name;
    private String passwordHash;
    private String email;
    private byte isAdmin;

    public User() {

    }

    public User(String name, String passwordHash, String email, byte isAdmin) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public int getId(){ return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getStrAdmin(){
        String str="";
        switch (isAdmin) {
            case 1:
                str = "CLIENTE";
                break;
            case 2:
                str = "VENDEDOR";
                break;
            default:
                str = "";
        }
        return str;
    }
}
