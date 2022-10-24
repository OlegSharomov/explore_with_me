package ru.practicum.users.service;

import org.springframework.stereotype.Service;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.entity.User;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getUsers(Integer[] ids, Integer from, Integer size);

    UserDto createUser(NewUserRequest newUserRequest);

    void removeUser(Integer userId);

    User getEntityUserById(Integer userId);
}
