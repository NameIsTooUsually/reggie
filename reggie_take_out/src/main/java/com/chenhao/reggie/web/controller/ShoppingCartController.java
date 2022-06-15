package com.chenhao.reggie.web.controller;

import com.chenhao.reggie.entity.ShoppingCart;
import com.chenhao.reggie.service.ShoppingCartService;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    //添加进购物车信息
    @PostMapping("/add")
    public R add(@RequestBody ShoppingCart shoppingCart) {

        ShoppingCart shoppingCart1 = shoppingCartService.add(shoppingCart);

        if (null != shoppingCart) {
            return R.success("添加成功",shoppingCart);
        }
        return R.fail("添加失败");
    }

    //减少购物车信息
    @PostMapping("/sub")
    public R sub(@RequestBody ShoppingCart shoppingCart){

        ShoppingCart subShopCart = shoppingCartService.sub(shoppingCart);
        if(null!=subShopCart){
            return R.success("修改成功",shoppingCart);
        }
        return R.fail("失败");
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        List<ShoppingCart> shoppingCartList = shoppingCartService.listByUserId();
       /* if(null!=shoppingCartList&&shoppingCartList.size()>0){
            return R.success("查询成功",shoppingCartList);
        }
        return R.fail("查询失败");*/
       if(null!=shoppingCartList){
           return R.success("查询成功",shoppingCartList);
       }
        return R.fail("查询失败");
    }

    //清空购物车
    @DeleteMapping("/clean")
    public R clean(){
        boolean clearResult = shoppingCartService.cleanByUesrId();
        if(clearResult){
            return R.success("清除成功");
        }
        return R.fail("清空失败");
    }
}