package Kopylov.webrise.repository;

import Kopylov.webrise.domain.dto.SubscriptionTopDto;
import Kopylov.webrise.domain.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT new Kopylov.webrise.domain.dto.SubscriptionTopDto(s.name, s.months, s.price, COUNT(us.subscription.id)) " +
            "FROM Subscription s " +
            "JOIN s.userSubscriptions us " +
            "GROUP BY s.name, s.months, s.price " +
            "ORDER BY COUNT(us.subscription.id) DESC")
    List<SubscriptionTopDto> findTopSubscriptions(Pageable pageable);
}
