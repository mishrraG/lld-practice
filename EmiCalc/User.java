package EmiCalc;

//Template Design Pattern
public abstract class User {
    public String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    
}
