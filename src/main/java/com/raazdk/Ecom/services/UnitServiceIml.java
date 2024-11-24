package com.raazdk.Ecom.services;

import com.raazdk.Ecom.models.Unit;
import com.raazdk.Ecom.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceIml implements UnitService{
    @Autowired
    UnitRepository repository;
    @Override
    public List<Unit> loadUnitList() {

        return repository.findAll();

    }
}
