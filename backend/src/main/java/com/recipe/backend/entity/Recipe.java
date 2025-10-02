package com.recipe.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Data
@Entity
@Table(name = "recipe")
@Indexed(index = "idx_recipe")
public class Recipe {

    private Long id;

    @FullTextField
    private String name;

    private String cuisine;
}
