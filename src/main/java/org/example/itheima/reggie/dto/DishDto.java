package org.example.itheima.reggie.dto;

import lombok.Data;
import org.example.itheima.reggie.entity.Dish;
import org.example.itheima.reggie.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
