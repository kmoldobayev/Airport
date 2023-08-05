package kg.kuban.airport.enums;

public enum UserStatus {
    BLOCKED("Заблокированный"),
    DISMISSED("Уволенный"),
    ACTIVE("Действующий");

    private String name;

    private UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
