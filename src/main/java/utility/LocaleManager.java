package utility;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocaleManager {

    private Locale currentLocale = new Locale("en_CA");
    private ResourceBundle bundle = ResourceBundle.getBundle("loc", currentLocale);

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setLocale(String loc) {
        currentLocale = new Locale(loc);
        bundle = ResourceBundle.getBundle("loc", currentLocale);
    }

    public String localize(String key) {
        try {
            return new String(bundle.getString(key).getBytes("Windows-1251"), StandardCharsets.UTF_8);
        } catch (MissingResourceException | NullPointerException | UnsupportedEncodingException e) {
            return key;
        }
    }

}
