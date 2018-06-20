package sda.pl;

import java.math.BigDecimal;

public class ProjectHelper {
    public static Long convertStringToLong(String string) {
        try {
            return Long.valueOf(string.trim());
        } catch (NumberFormatException nfe) {
            return 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    public static BigDecimal convertStringToBigDecimal(String string) {
        try {
            return new BigDecimal(string.trim().replace(",", "."));
        } catch (NumberFormatException nfe) {
            return BigDecimal.ZERO;
        } catch (Exception ex) {
            ex.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}