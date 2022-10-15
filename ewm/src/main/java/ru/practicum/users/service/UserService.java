package ru.practicum.users.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.entity.User;

import java.util.List;
@Service
public interface UserService {
    @Transactional
    List<UserDto> getUsers(Integer[] ids, Integer from, Integer size);

    @Transactional(readOnly = false)
    UserDto createUser(NewUserRequest newUserRequest);

    @Transactional(readOnly = false)
    void removeUser(Integer userId);

    @Transactional
    boolean isUserExist(Integer userId);

    @Transactional(readOnly = true)
    User getEntityUserById(Integer userId);
}
