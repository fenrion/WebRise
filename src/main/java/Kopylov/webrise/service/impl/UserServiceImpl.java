package Kopylov.webrise.service.impl;

import Kopylov.webrise.domain.dto.SubscriptionForUserDto;
import Kopylov.webrise.domain.dto.UserDto;
import Kopylov.webrise.domain.dto.UserWithSubsDto;
import Kopylov.webrise.domain.exception.NotFoundException;
import Kopylov.webrise.domain.mapper.UserMapper;
import Kopylov.webrise.domain.model.Subscription;
import Kopylov.webrise.domain.model.User;
import Kopylov.webrise.domain.model.UserSubs;
import Kopylov.webrise.repository.SubscriptionRepository;
import Kopylov.webrise.repository.UserRepository;
import Kopylov.webrise.repository.UserSubsRepository;
import Kopylov.webrise.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubsRepository userSubsRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           SubscriptionRepository subscriptionRepository,
                           UserSubsRepository userSubsRepository,
                           UserMapper userMapper
                           ) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userSubsRepository = userSubsRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        log.info("Сохранение нового пользователя");
        User user = userRepository.save(userMapper.userFromUserDto(userDto));
        return userMapper.userDtoFromUser(user);
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userID) {
        log.info("Поиск пользователя с id {}", userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден " + userID));
        return userMapper.userDtoFromUser(user);
    }

    @Override
    @Transactional
    public UserDto updateUserById(Long userID, UserDto userDto) {
        log.info("Поиск пользователя для обновления с id {}", userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
        BeanUtils.copyProperties(userDto, user);
        return userMapper.userDtoFromUser(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userID) {
        log.info("Удаление пользователя с id {}", userID);
        userRepository.deleteById(userID);
    }

    @Override
    @Transactional
    public UserWithSubsDto addSubscriptionToUser(Long userID, Long subscriptionID) {
        log.info("Поиск пользователя с id {} для добавления подписки", userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));

        log.info("Поиск подписки с id {}", subscriptionID);
        Subscription subscription = subscriptionRepository.findById(subscriptionID)
                .orElseThrow(() -> new NotFoundException("Не найдена подписка с id " + subscriptionID));

        UserSubs userSubs = new UserSubs();
        userSubs.setUser(user);
        userSubs.setSubscription(subscription);
        userSubs.setStartAt(LocalDate.now());
        log.info("Добавление подписки");
        userSubsRepository.save(userSubs);

        List<SubscriptionForUserDto> subsList = convertToSubscriptionForUserDto(user);
        return new UserWithSubsDto(user.getName(), subsList);
    }

    @Override
    @Transactional
    public UserWithSubsDto getUserSubscriptions(Long userID) {
        log.info("Поиск пользователя с id {}", userID);
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
        return new UserWithSubsDto(user.getName(), convertToSubscriptionForUserDto(user));
    }

    @Override
    @Transactional
    public UserWithSubsDto deleteSubscriptionFromUser(Long userID, Long subscriptionID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
        log.info("Удаление у пользователя с id {} подписки с id {}", userID, subscriptionID);
        userSubsRepository.deleteByUserIdAndSubscriptionId(userID, subscriptionID);
        return new UserWithSubsDto(user.getName(), convertToSubscriptionForUserDto(user));
    }

    private List<SubscriptionForUserDto> convertToSubscriptionForUserDto(User user){
        List<SubscriptionForUserDto> subsList = new ArrayList<>();
        for(UserSubs sub : user.getUserSubscriptions()){
            SubscriptionForUserDto subTmp = new SubscriptionForUserDto(
                    sub.getSubscription().getId(),
                    sub.getSubscription().getName(),
                    sub.getSubscription().getPrice(),
                    sub.getStartAt(),
                    sub.getStartAt().plusMonths(sub.getSubscription().getMonths())
            );
            subsList.add(subTmp);
        }
        return subsList;
    }
}
