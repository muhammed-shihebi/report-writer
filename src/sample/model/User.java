/*
 * @Datei           User.java
 * @Autor           Muhammednur Şehebi
 * @Matrikelnummer  170503112
 * @Date            6/20/2020
 */

package sample.model;

public class User {

    public final static int LEVEL1 = 1;
    public final static int LEVEL2 = 2;
    public final static int LEVEL3 = 3;
    public final static int MAXPASSWORDLENGTH = 50;
    public final static int MINPASSWORDLENGTH = 8;

    private String username = "";
    private String password= "";
    private String name= "";
    private String surname= "";
    private int level = 0;

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

    public void setPassword(String password){
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

    @Override
    public String toString(){
        return this.getName() + " " + this.getSurname();
    }
}