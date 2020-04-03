package sample.model;

public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private int level;

    public User(){}

    public User(String username, String password, String name, String surname, int level) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
