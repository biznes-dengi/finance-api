package com.finance.app.auth.utility;

public class Checker {

    /**
     * @return Return true if value is username, false otherwise.
     */
    public static boolean checkIfGivenValueIsEmail(String value) {
        return value.contains("@");
    }
}
