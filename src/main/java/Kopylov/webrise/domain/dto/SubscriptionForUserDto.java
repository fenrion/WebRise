package Kopylov.webrise.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionForUserDto(
        Long id,
        String name,
        BigDecimal price,
        LocalDate startDate,
        LocalDate finishDate
) {
}
