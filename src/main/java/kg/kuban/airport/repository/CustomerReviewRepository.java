package kg.kuban.airport.repository;

import kg.kuban.airport.entity.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long>, QuerydslPredicateExecutor<CustomerReview> {
}
