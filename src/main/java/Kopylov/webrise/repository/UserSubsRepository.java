package Kopylov.webrise.repository;

import Kopylov.webrise.domain.model.UserSubs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserSubsRepository extends JpaRepository<UserSubs, Long> {

    @Modifying
    @Query("DELETE FROM UserSubs us WHERE us.user.id = :userId AND us.subscription.id = :subscriptionId")
    void deleteByUserIdAndSubscriptionId(Long userId, Long subscriptionId);

    @Modifying
    @Query(value = "DELETE FROM users_subs WHERE (start_at + (SELECT (months || ' month')::interval FROM subscriptions WHERE id = subscription_id))::date <= :today", nativeQuery = true)
    void deleteExpiringSubscriptions(LocalDate today);
}
