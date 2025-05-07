package Kopylov.webrise.controller;

import Kopylov.webrise.domain.dto.SubscriptionTopDto;
import Kopylov.webrise.service.SubscriptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/top")
    public List<SubscriptionTopDto> getPopularSubscriptions() {
        return subscriptionService.getTopSubscriptions();
    }
}
