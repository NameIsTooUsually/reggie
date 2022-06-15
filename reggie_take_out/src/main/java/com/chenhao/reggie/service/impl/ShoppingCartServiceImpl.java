package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.ShoppingCart;
import com.chenhao.reggie.mapper.ShoppingCartMapper;
import com.chenhao.reggie.service.ShoppingCartService;
import com.chenhao.reggie.utils.BaseContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        //添加用户id
        Long userId = BaseContextUtil.getCurrentId();
        shoppingCart.setUserId(userId);

        //判断添加的是菜品还是套餐
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        //创建查询条件对象
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();

        if(null!=dishId){
            //添加的是菜品信息，
            qw.eq(ShoppingCart::getDishId,dishId);
        }else {
            //添加的是套餐信息
            qw.eq(ShoppingCart::getSetmealId,setmealId);
        }
        qw.eq(ShoppingCart::getUserId,userId);
        //查询购物车是否已经添加过该商品
        ShoppingCart shopData = getOne(qw);
        if (null!=shopData) {
            //说明该菜品已经添加过了，需要给数量增加一个
            shopData.setNumber(shopData.getNumber()+1);
        }else{
            //说明该菜品或套餐没有添加过，需要给数量赋值1
            shopData=shoppingCart;
            shopData.setNumber(1);
        }

        //统一保存
        boolean saveOrUpdate = saveOrUpdate(shopData);

        if(saveOrUpdate){
            return shoppingCart;
        }
        return null;
    }

    //查询用户购物车信息
    @Override
    public List<ShoppingCart> listByUserId() {
        //获取用户id
        Long userId = BaseContextUtil.getCurrentId();
        //查询购物车信息
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId,userId);
        //获取用户购物车信息
        List<ShoppingCart> list = list(qw);
        return list;
    }

    //清空用户购物车信息
    @Override
    public boolean cleanByUesrId() {
        //获取用户id
        Long userId = BaseContextUtil.getCurrentId();
        //创建查询条件对象
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId,userId);
        boolean removeResult = remove(qw);
        return removeResult;
    }

    @Override
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        //获取用户id
        Long userId = BaseContextUtil.getCurrentId();
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        //创建查询对象
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId,userId);

        //判断是产品信息，还是套餐信息
        if(null!=dishId){
            qw.eq(ShoppingCart::getDishId,dishId);
        }else{
            qw.eq(ShoppingCart::getSetmealId,setmealId);
        }
        //获取该物品购物车信息
        ShoppingCart sc = getOne(qw);

        if(null==sc){
            return null;
        }else{
            //获取该物品数量
            Integer number = sc.getNumber();
            if(number>1){
                sc.setNumber(number-1);
                //重新添加到数据库
                boolean save = updateById(sc);

                return sc;
            }else {
                removeById(sc);
                sc.setNumber(0);
                return sc;
            }
        }
    }
}