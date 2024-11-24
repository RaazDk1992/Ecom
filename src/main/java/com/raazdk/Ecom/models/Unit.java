package com.raazdk.Ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    Long id;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    private UnitsList unitname;

    @OneToMany(mappedBy = "unit",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @JsonBackReference
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();

    public Unit(UnitsList unit){
        this.unitname = unit;
    }
}
