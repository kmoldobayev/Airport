package kg.kuban.airport.enums;

public enum PasswordRules {
    PASSWORD_RULE1("Пароль должен состоять только из латинских букв!"),
    PASSWORD_RULE2("Пароль не должен содержать пробел!"),
    PASSWORD_RULE3("Минимальная длина пароля 5 символов!"),
    PASSWORD_RULE4("Пароль должен содержать хотя бы одну заглавную букву (A-Z)"),
    PASSWORD_RULE5("Пароль должен содержать хотя бы одну цифру (0-9)"),
    PASSWORD_RULE6("Пароль должен содержать хотя бы один специальный символ!");// @, #, %, &, !, $
    private String description;

    PasswordRules(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
