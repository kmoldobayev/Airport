package kg.kuban.airport.validator;

import kg.kuban.airport.enums.LoginRules;
import kg.kuban.airport.enums.PasswordRules;
import kg.kuban.airport.exception.InvalidLoginException;
import kg.kuban.airport.exception.InvalidPasswordException;

public class UserRegistrationValidator {
    protected Boolean isLoginOk;
    protected Boolean isPasswordOk;

    public UserRegistrationValidator(Boolean isLoginOk, Boolean isPasswordOk) {
        this.isLoginOk = isLoginOk;
        this.isPasswordOk = isPasswordOk;
    }

    public Boolean getLoginOk() {
        return isLoginOk;
    }

    public void setLoginOk(Boolean loginOk) {
        isLoginOk = loginOk;
    }

    public Boolean getPasswordOk() {
        return isPasswordOk;
    }

    public void setPasswordOk(Boolean passwordOk) {
        isPasswordOk = passwordOk;
    }

    public boolean checkCyrillic(String string) {
        boolean isCyrillic = string.chars()
                .mapToObj(Character.UnicodeBlock::of)
                .anyMatch(b -> b.equals(Character.UnicodeBlock.CYRILLIC));
        return isCyrillic;
    }
    public boolean checkSpace(String string) {
        if (string.contains(" ")) {
            return true;
        }
        return false;
    }
    public boolean checkDigits(String string) {
        for (int i = 0; i <= 9; i++) {
            String str1 = Integer.toString(i);
            if (string.contains(str1)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkOneDigit(String string) {
        int count = 0;
        for (int i = 0; i <= 9; i++) {
            String str1 = Integer.toString(i);
            if (string.contains(str1)) {
                count = 1;
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }
    public boolean checkSpecSymbols(String string) {
        if ((string.contains("@") || string.contains("#")
                || string.contains("!") || string.contains("~")
                || string.contains("$") || string.contains("%")
                || string.contains("^") || string.contains("&")
                || string.contains("*") || string.contains("(")
                || string.contains(")") || string.contains("-")
                || string.contains("+") || string.contains("/")
                || string.contains(":") || string.contains(".")
                || string.contains(", ") || string.contains("<")
                || string.contains(">") || string.contains("?")
                || string.contains("|"))) {
            return true;
        }
        return false;
    }
    public boolean checkNoSpecSymbols(String string) {
        if (!(string.contains("@") || string.contains("#")
                || string.contains("!") || string.contains("~")
                || string.contains("$") || string.contains("%")
                || string.contains("^") || string.contains("&")
                || string.contains("*") || string.contains("(")
                || string.contains(")") || string.contains("-")
                || string.contains("+") || string.contains("/")
                || string.contains(":") || string.contains(".")
                || string.contains(", ") || string.contains("<")
                || string.contains(">") || string.contains("?")
                || string.contains("|"))) {
            return true;
        }
        return false;
    }
    public boolean checkLength(String string, int left, int right) {
        //Не должен быть короче 3 символов и длиннее чем 10 символов.
        if (!((string.length() >= left) && (string.length() <= right))) {
            return true;
        }
        return false;
    }
    public boolean checkBigSymbol(String string) {
        int count = 0;

        for (int i = 65; i <= 90; i++) {
            char c = (char)i;
            String str1 = Character.toString(c);
            if (string.contains(str1)) {
                count = 1;
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }
    public void isValidLogin(String login) throws InvalidLoginException {
        if (checkCyrillic(login) == true) {
            throw new InvalidLoginException(LoginRules.LOGIN_RULE1.name());
        }
        if (checkSpace(login) == true) {
            throw new InvalidLoginException(LoginRules.LOGIN_RULE2.name());
        }
        if (checkDigits(login) == true) {
            throw new InvalidLoginException(LoginRules.LOGIN_RULE3.name());
        }
        if (checkSpecSymbols(login) == true){
            throw new InvalidLoginException(LoginRules.LOGIN_RULE4.name());
        }
        //Не должен быть короче 3 символов и длиннее чем 10 символов.
        if (checkLength(login, 3, 10) == true){
            throw new InvalidLoginException(LoginRules.LOGIN_RULE5.name());
        }
        isLoginOk = true;
    }

    public void isValidPassword(String password) throws InvalidPasswordException {
        if (checkCyrillic(password) == true) {
            throw new InvalidPasswordException(PasswordRules.PASSWORD_RULE1.name());
        }
        if (checkSpace(password) == true) {
            throw new InvalidPasswordException(PasswordRules.PASSWORD_RULE2.name());
        }
        if (checkLength(password, 5, 128) == true){
            throw new InvalidPasswordException(PasswordRules.PASSWORD_RULE3.name());
        }

        if (checkBigSymbol(password) == true) {
            throw new InvalidPasswordException(PasswordRules.PASSWORD_RULE4.name());
        }
        if (checkOneDigit(password) == true) {
            throw new InvalidPasswordException(PasswordRules.PASSWORD_RULE5.name());
        }
        if (checkNoSpecSymbols(password) == true){
            throw new InvalidPasswordException(PasswordRules.PASSWORD_RULE6.name());
        }

        // Пароль валидный
        this.setPasswordOk(true);
    }
}
