package kg.kuban.airport.enums;

public enum AirplaneType {
    BOEING("Boeing"),
    AIRBUS("Airbus"),
    EMBRAER("Embraer"),
    TU("Ту"),
    IL("Ил"),
    SUPERJET("Суперджет"),
    LOCKHEED("Lockheed");

    private String name;

    private AirplaneType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
