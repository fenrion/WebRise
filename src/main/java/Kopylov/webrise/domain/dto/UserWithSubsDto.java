package Kopylov.webrise.domain.dto;

import java.util.List;

public record UserWithSubsDto(
        String name,
        List<SubscriptionForUserDto> subs
) {
}
