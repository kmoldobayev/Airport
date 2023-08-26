package kg.kuban.airport.enums;

public enum LoginRules {
    LOGIN_RULE1("Логин пользователя должен состоять только из латинских букв!"),
    LOGIN_RULE2("В логине не должно быть пробелов!"),
    LOGIN_RULE3("В логине не должно быть цифр!"),
    LOGIN_RULE4("В логине не должно быть спецсимволов!"),
    LOGIN_RULE5("Логин не должен быть короче 3 символов и длиннее чем 10 символов!");



    private String description;

    LoginRules(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
