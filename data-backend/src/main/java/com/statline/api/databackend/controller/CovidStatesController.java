package com.statline.api.databackend.controller;

import com.statline.api.databackend.dao.CovidStatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/states")
public class CovidStatesController {
    @Autowired
    private CovidStatesRepository states;
    @GetMapping(path="/latest")
    List<Object[]> getLatest(){
        return states.getLatest();
    }
}