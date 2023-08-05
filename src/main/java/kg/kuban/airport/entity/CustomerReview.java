package kg.kuban.airport.entity;

import kg.kuban.airport.enums.Mark;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_reviews")
public class CustomerReview {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @Column(name = "review")
    private String review;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "mark")
    private Mark mark;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser appUser;


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

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public CustomerReview setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
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
}
