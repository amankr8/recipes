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

    @FullTextField
    @Column(name = "r_cuisine")
    private String cuisine;

    @Column(name = "r_cook_time_minutes")
    private Long cookTimeMinutes;

    @ElementCollection
    @FullTextField
    @Column(name = "r_tags")
    private List<String> tags;
}
