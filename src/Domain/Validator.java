package Domain;

import java.util.Calendar;

public class Validator {

    public static void validate(Car entity) throws IllegalArgumentException {
        if (entity.getCompany().length() < 3 || entity.getModel().length() < 1 || entity.getYear() < 1900 ||
                entity.getYear() > Calendar.getInstance().get(Calendar.YEAR) || entity.getLink().length() < 9) {
            throw new IllegalArgumentException();
        }
    }
}
