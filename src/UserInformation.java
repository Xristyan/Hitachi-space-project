public class UserInformation {
    private String emailOfTheSender;
    private String password;
    private String emailOfTheReceiver;

    public UserInformation(String emailOfTheSender, String password, String emailOfTheReceiver) {
        this.emailOfTheSender = emailOfTheSender;
        this.password = password;
        this.emailOfTheReceiver = emailOfTheReceiver;
    }

    public String getEmailOfTheSender() {
        return emailOfTheSender;
    }

    public void setEmailOfTheSender(String emailOfTheSender) {
        this.emailOfTheSender = emailOfTheSender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailOfTheReceiver() {
        return emailOfTheReceiver;
    }

    public void setEmailOfTheReceiver(String emailOfTheReceiver) {
        this.emailOfTheReceiver = emailOfTheReceiver;
    }
}
