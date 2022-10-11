package ru.practicum.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.UserMapper;
import ru.practicum.users.model.User;
import ru.practicum.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public List<UserDto> getUsers(Integer[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id"));
        Page<User> users;
        if (ids != null) {
            users = userRepository.findAllByIdIn(List.of(ids), pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = userMapper.toUser(newUserRequest);
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Transactional(readOnly = false)
    public void removeUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public boolean isUserExist(Integer userId) {
        return userRepository.existsById(userId);
    }
}
