// PersonnummerValidator.java
/**
 * Validates Swedish personal identity numbers (personnummer).
 * Implements the Luhn algorithm for checksum validation.
 */
public class PersonnummerValidator {
    /**
     * Validates a Swedish personnummer format and checksum.
     * @param personnummer 10-digit personal number
     * @return true if valid
     */
    public static boolean validate(String personnummer) {
        if (!personnummer.matches("\\d{10}")) {
            return false;
        }

        return calculateChecksum(personnummer) ==
                Character.getNumericValue(personnummer.charAt(9));
    }

    private static int calculateChecksum(String personnummer) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(personnummer.charAt(i));
            digit *= (i % 2 == 0) ? 2 : 1;
            sum += digit > 9 ? digit - 9 : digit;
        }
        return (10 - (sum % 10)) % 10;
    }
}