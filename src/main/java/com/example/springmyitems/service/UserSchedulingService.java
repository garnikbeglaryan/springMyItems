package com.example.springmyitems.service;

import org.apache.commons.lang3.StringUtils;
import com.example.springmyitems.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSchedulingService {


    private final UserService userService;

    //    @Scheduled(fixedDelay = 3000)
    @Scheduled(cron = "0 0 0 1 1/1 *")
    public void removeExpiredTokens() {
        List<User> all = userService.findAll();
        for (User user : all) {
            //25.03.2022 22:00
            LocalDateTime tokenCreatedDate = user.getTokenCreatedDate();
            if (tokenCreatedDate != null) {
                //26.03.2022 22:00
                LocalDateTime expireDateTime = tokenCreatedDate.plusDays(1);
                if (StringUtils.isNotEmpty(user.getToken())
                        && LocalDateTime.now().isAfter(expireDateTime)) {
                    user.setToken(null);
                    user.setTokenCreatedDate(null);
                    userService.save(user);
                }
            }
        }
    }
}
