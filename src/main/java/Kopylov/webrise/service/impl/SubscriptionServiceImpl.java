package Kopylov.webrise.service.impl;

import Kopylov.webrise.domain.dto.SubscriptionTopDto;
import Kopylov.webrise.repository.SubscriptionRepository;
import Kopylov.webrise.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public List<SubscriptionTopDto> getTopSubscriptions() {
        log.info("Поиск 3 самых популярных подписок");
        Pageable pageable = PageRequest.of(0, 3);
        return subscriptionRepository.findTopSubscriptions(pageable);
    }
}
