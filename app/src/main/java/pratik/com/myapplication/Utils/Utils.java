package pratik.com.myapplication.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * Checks if the input parameter is a valid email.
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        } else {
            final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Matcher matcher;
            Pattern pattern = Pattern.compile(emailPattern);
            matcher = pattern.matcher(email);

            return matcher.matches() && matcher.matches();
        }
    }
}
