package ru.practicum.compilations.publ;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/compilations")
public class CompilationsPublicController {

    //Получение подборок событий
    @GetMapping               // искать только закрепленные/не закрепленные подборки
    public void getCompilations(@RequestParam(required = false) Boolean pinned,
                                // количество элементов, которые нужно пропустить для формирования текущего набора
                                @RequestParam(required = false, defaultValue = "10") Integer from,
                                // количество элементов в наборе
                                @RequestParam(required = false, defaultValue = "10") Integer size) {

    }

    // Получение подборки событий по id
    @GetMapping("/compId")
    public void getCompilationById(@PathVariable Integer compId){

    }
}
