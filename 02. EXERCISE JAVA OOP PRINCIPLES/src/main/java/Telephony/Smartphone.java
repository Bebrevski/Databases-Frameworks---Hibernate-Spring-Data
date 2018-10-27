package Telephony;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Smartphone implements Callable, Browser {

    public Smartphone() {
    }

    public String browse(String website) {

        String validationPattern = "[0-9]+";
        Matcher matcher = Pattern.compile(validationPattern).matcher(website);

        if(matcher.find()) {
            throw new IllegalArgumentException("Invalid URL!");
        }

        return String.format("Browsing: %s!", website);
    }

    public String call(String phoneNumber) {

        String phoneNumberPattern = "[a-z]+";
        Matcher matcher = Pattern.compile(phoneNumberPattern).matcher(phoneNumber);

        if(matcher.find()) {
            throw new IllegalArgumentException("Invalid number!");
        }

        return String.format("Calling... %s", phoneNumber);
    }
}
