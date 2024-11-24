package com.raazdk.Ecom.controller;

import com.raazdk.Ecom.models.Unit;
import com.raazdk.Ecom.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitsController {

    @Autowired
    UnitService unitService;
    @GetMapping("/getunits")
    public List<Unit> getUnitList(){

        return unitService.loadUnitList();
    }

}
