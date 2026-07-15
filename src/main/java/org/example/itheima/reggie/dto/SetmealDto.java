package org.example.itheima.reggie.dto;

import lombok.Data;
import org.example.itheima.reggie.entity.Setmeal;
import org.example.itheima.reggie.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
