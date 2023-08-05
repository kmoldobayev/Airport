package kg.kuban.airport.enums;

public enum Mark {
    ONE_STAR(1),
    TWO_STARS(2),
    THREE_STARS(3),
    FOUR_STARS(4),
    FIVE_STARS(5);

    private Integer num;

    private Mark(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }
}
