package Kopylov.webrise.service;

import Kopylov.webrise.domain.dto.SubscriptionTopDto;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionTopDto> getTopSubscriptions();
}
