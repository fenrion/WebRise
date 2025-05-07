package Kopylov.webrise.service;

import Kopylov.webrise.domain.dto.UserDto;
import Kopylov.webrise.domain.dto.UserWithSubsDto;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto getUserById(Long userID);

    UserDto updateUserById(Long userID, UserDto userDto);

    void deleteUserById(Long userID);

    UserWithSubsDto addSubscriptionToUser(Long userID, Long subscriptionID);

    UserWithSubsDto getUserSubscriptions(Long userID);

    UserWithSubsDto deleteSubscriptionFromUser(Long userID, Long subscriptionID);
}
