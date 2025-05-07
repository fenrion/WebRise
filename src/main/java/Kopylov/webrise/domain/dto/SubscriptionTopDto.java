package Kopylov.webrise.domain.dto;

import java.math.BigDecimal;

public record SubscriptionTopDto(
        String name,
        Integer months,
        BigDecimal price,
        Long subscriptionCount
) {
}
