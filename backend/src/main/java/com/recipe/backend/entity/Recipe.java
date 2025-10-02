package com.recipe.backend.entity;

import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "recipe")
@Indexed(index = "idx_recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long id;

    @FullTextField
    @Column(name = "r_name")
    private String name;

    @Column(name = "r_cuisine")
    private String cuisine;

    @Column
    private Long cookTimeMinutes;

    @Column
    private List<String> tags;
}
