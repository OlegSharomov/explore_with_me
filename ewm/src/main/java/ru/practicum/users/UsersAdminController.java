package ru.practicum.users;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.users.dto.NewUserRequest;

import javax.validation.Valid;

@RestController("/admin/users")
public class UsersAdminController {

    @GetMapping
    // Получение информации о пользователях. Возвращает list of UserDto.
    /* Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
     * либо о конкретных (учитываются указанные идентификаторы)*/
    public void getUsers(@RequestParam Integer[] ids,
                         @RequestParam(defaultValue = "0") Integer from,
                         @RequestParam(defaultValue = "10") Integer size) {

    }

    @PostMapping
    // Добавление нового пользователя. Возвращает UserDto.
    public void createUser(@Valid @RequestBody NewUserRequest newUserRequest) {

    }

    @DeleteMapping("/{userId}")
    // Удаление пользователя. Возвращает только статус ответа или ошибку.
    public void removeUser(@PathVariable Integer userId) {

    }

}
