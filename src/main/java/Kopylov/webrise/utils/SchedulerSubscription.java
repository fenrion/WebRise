package Kopylov.webrise.utils;

import Kopylov.webrise.repository.UserSubsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SchedulerSubscription {

    private static final Logger log = LoggerFactory.getLogger(SchedulerSubscription.class);
    private final UserSubsRepository userSubsRepository;

    public SchedulerSubscription(UserSubsRepository userSubsRepository) {
        this.userSubsRepository = userSubsRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkSubscriptionExpirations() {
        log.info("Удаление неактивных подписок");
        userSubsRepository.deleteExpiringSubscriptions(LocalDate.now());
    }
}
