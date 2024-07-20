package ru.ibash.quotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ibash.quotes.models.Quote;
import ru.ibash.quotes.repository.QuoteRepository;
import ru.ibash.quotes.service.QuotService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private QuotService service;

    @Autowired
    private QuoteRepository repository;

    @GetMapping("/all")
    public ResponseEntity<List<Quote>> getAll(@RequestParam(required = false,defaultValue = "1")String page){
        int _page =1;
        try {
            _page = Integer.parseInt(page);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        var res = repository.findAll(PageRequest.of(_page-1,5));
        return new ResponseEntity<>(res.stream().collect(Collectors.toList()), HttpStatus.OK);



    }
    @GetMapping("/page")
    public ResponseEntity<List<Quote>> getPage(@RequestParam(required = false,defaultValue = "1")String page){
        int _page =1;
        try {
            _page = Integer.parseInt(page);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        var res = service.getPage(_page);
        return new ResponseEntity<>(res, HttpStatus.OK);



    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getById(@PathVariable("id")int id){
        var res = service.getById(id);
        if (res == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(res, HttpStatus.OK);



    }

    @GetMapping("/random")
    public ResponseEntity<Quote> getRandom(){
        var res = service.getRandom();
        return new ResponseEntity<>(res, HttpStatus.OK);



    }
}
