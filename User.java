public class User {
    String username;
    private String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
       
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser(User user)
    {
        this.username=user.getUsername();
        this.password=user.getPassword();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin(String username, String password)
    {
        return (username=="admin")&&(password=="admin");
    }

    
}