package ru.practicum.compilations.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/compilations")
public class CompilationsPublicController {

    @GetMapping
    //Получение подборок событий. Возвращает список CompilationDto.
                                // Искать только закрепленные/не закрепленные подборки
    public void getCompilations(@RequestParam(required = false) Boolean pinned,
                                // Количество элементов, которые нужно пропустить для формирования текущего набора
                                @RequestParam(defaultValue = "10") Integer from,
                                // Количество элементов в наборе
                                @RequestParam(defaultValue = "10") Integer size) {

    }


    @GetMapping("/{compId}")
    // Получение подборки событий по id. Возвращает CompilationDto
    public void getCompilationById(@PathVariable Integer compId){

    }
}
