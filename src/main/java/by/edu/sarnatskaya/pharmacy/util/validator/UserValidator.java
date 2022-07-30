package by.edu.sarnatskaya.pharmacy.util.validator;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;

public class UserValidator {
    private static final String LOGIN_REGEX = "[A-Za-z]\\w{1,21}";
    private static final String PASSPORT_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\w+$).{6,25}$";
    private static final String NAME_REGEX = "[A-Za-zА-Я-а-я-]{1,41}";
    private static final String EMAIL_REGEX = "^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String EMAIL_SYMBOL_NUMBER = ".{8,50}";
    private static final String PHONE_NUMBER_REGEX = "^\\+375\\d{9}$";
    private static final String WRONG_SYMBOL_PATTERN = "[\\p{Punct}&&[^-]]|-{2,}| {2,}|^[- ]|[- ]$";

    private static final UserValidator instance = new UserValidator();

    public UserValidator() {
    }

    public static UserValidator getInstance() {
        return instance;
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSPORT_REGEX);
    }

    public static boolean isSurnameValid(String surname) {
        return surname != null && surname.isEmpty() && surname.matches(NAME_REGEX);
    }

    public static boolean isNameValid(String name) {
        Matcher matcher = Pattern.compile(WRONG_SYMBOL_PATTERN).matcher(name);
        return name != null && name.matches(NAME_REGEX);
    }

    public static boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX) && email.matches(EMAIL_SYMBOL_NUMBER);
    }

    public static boolean isLoginValid(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean checkUserData(Map<String, String> userData) {
        boolean isValid = true;
        if (isSurnameValid(userData.get(SURNAME))) {
            userData.put(SURNAME, userData.get(SURNAME) + WRONG_SYMBOL_PATTERN);
            isValid = false;
        }
        if (isNameValid(userData.get(NAME))) {
            userData.put(NAME, userData.get(NAME) + WRONG_SYMBOL_PATTERN);
            isValid = false;
        }
        if (isLoginValid(userData.get(LOGIN))) {
            userData.put(LOGIN, userData.get(LOGIN) + WRONG_SYMBOL_PATTERN);
            isValid = false;
        }
        if(isEmailValid(userData.get(EMAIL))){
            userData.put(EMAIL, userData.get(EMAIL) +WRONG_SYMBOL_PATTERN);
            isValid = false;
        }
        if(isPhoneNumberValid(userData.get(PHONE))){
            userData.put(PHONE, userData.get(PHONE) +WRONG_SYMBOL_PATTERN);
            isValid = false;
        }
        if(isPasswordValid(userData.get(PASSWORD))){
            userData.put(PASSWORD, userData.get(PASSWORD) +WRONG_SYMBOL_PATTERN);
            isValid = false;
        }
        return isValid;
    }
}
