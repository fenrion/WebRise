package Kopylov.webrise.controller;

import Kopylov.webrise.domain.dto.UserDto;
import Kopylov.webrise.domain.dto.UserWithSubsDto;
import Kopylov.webrise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping("/{userID}")
    public UserDto getUserById(@PathVariable Long userID) {
        return userService.getUserById(userID);
    }

    @PutMapping("/{userID}")
    public UserDto updateUserById(@PathVariable Long userID, @Valid @RequestBody UserDto userDto) {
        return userService.updateUserById(userID, userDto);
    }

    @DeleteMapping("/{userID}")
    public void deleteUserById(@PathVariable Long userID) {
        userService.deleteUserById(userID);
    }

    @PostMapping("/{userID}/subscriptions/{subscriptionID}")
    public UserWithSubsDto addSubscriptionToUser(@PathVariable Long userID, @PathVariable Long subscriptionID) {
        return userService.addSubscriptionToUser(userID, subscriptionID);
    }

    @GetMapping("{userID}/subscriptions")
    public UserWithSubsDto getUserSubscriptions(@PathVariable Long userID) {
        return userService.getUserSubscriptions(userID);
    }

    @DeleteMapping("/{userID}/subscriptions/{subscriptionID}")
    public UserWithSubsDto deleteSubscriptionFromUser(@PathVariable Long userID, @PathVariable Long subscriptionID) {
        return userService.deleteSubscriptionFromUser(userID, subscriptionID);
    }
}
