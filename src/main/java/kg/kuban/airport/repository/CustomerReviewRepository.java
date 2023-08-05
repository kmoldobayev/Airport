package kg.kuban.airport.repository;

import kg.kuban.airport.entity.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {
}
