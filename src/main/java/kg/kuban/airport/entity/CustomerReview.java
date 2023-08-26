package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.kuban.airport.enums.Mark;

import javax.persistence.*;
import java.time.LocalDateTime;

@Schema(name = "Сущность Отзывы клиента", description = "Описывает сущность отзывов клиента")
@Entity
@Table(name = "customer_reviews")
public class CustomerReview {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review")
    private String review;

    @Column(name = "date_register")
    private LocalDateTime dateRegister;

    @Enumerated(EnumType.STRING)
    @Column(name = "mark")
    private Mark mark;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;


    public CustomerReview() {
    }

    public Long getId() {
        return id;
    }

    public CustomerReview setId(Long id) {
        this.id = id;
        return this;
    }

    public String getReview() {
        return review;
    }

    public CustomerReview setReview(String review) {
        this.review = review;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public CustomerReview setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public Mark getMark() {
        return mark;
    }

    public CustomerReview setMark(Mark mark) {
        this.mark = mark;
        return this;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public CustomerReview setAppUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public Flight getFlight() {
        return flight;
    }

    public CustomerReview setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }
}
