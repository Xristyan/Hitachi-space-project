import java.util.regex.Pattern;

public class EmailValidation {
    private final static Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.]+@+[a-zA-Z0-9-.]+");

    public static boolean validation(String email)
    {
        if (!emailPattern.matcher(email).matches())
        {
            return false;
        }
        return true;
    }

}
