package com.chenhao.reggie.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenhao.reggie.entity.AddressBook;
import com.chenhao.reggie.service.AddressBookService;
import com.chenhao.reggie.web.R;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/14 19:52
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    HttpSession session;

    @Autowired
    AddressBookService addressBookService;

    //查询地址信息
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        LambdaQueryWrapper<AddressBook> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(AddressBook::getIsDefault);
        List<AddressBook> list = addressBookService.list(qw);
        if(null!=list){

            return R.success("查询地址成功",list);
        }

        return R.fail("查询失败");
    }

    //添加地址信息
    @PostMapping
    public R addAddress(@RequestBody AddressBook addressBook){
        if(null==addressBook){
            return R.fail("参数异常");
        }
        //设置add信息
        Long userId = (Long) session.getAttribute("user");
        addressBook.setUserId(userId);

        boolean saveResult = addressBookService.save(addressBook);
        if(saveResult){
            return R.success("添加成功");
        }
        return R.fail("添加失败");
    }
    //根据id修改地址为默认地址
    @PutMapping("/default")
    public R updateDefault(@RequestBody AddressBook addressBook){
        //判断id是否为null
        Long addressBookId = addressBook.getId();
        if(null!=addressBookId){
            boolean updateResult = addressBookService.updateDefault(addressBook);
            if(updateResult){
                return R.success("修改成功");

            }
            return R.fail("修改失败");
        }
        return R.fail("参数异常");
    }

    //根据id获取地址信息
    @GetMapping("{id}")
    public R<AddressBook> findById(@PathVariable Long id){
        //判断id是否异常
        if (null!=id){
            //根据id进行查询
            AddressBook addressBook = addressBookService.getById(id);

            if(null!=addressBook){
                return R.success("查询成功",addressBook);
            }
                return R.fail("查询失败");
        }
        return R.fail("参数异常");
    }

    //根据id修改地址信息
    @PutMapping
    public R updateById(@RequestBody AddressBook addressBook){
        //判断参数是否异常
        Long addressBookId = addressBook.getId();
        if(null!=addressBookId){
            //创建修改条件
            boolean updateResult = addressBookService.updateById(addressBook);
            if(updateResult){
                return R.success("修改成功");
            }
            return R.fail("修改失败");
        }
        return R.fail("参数异常");
    }

    //根据ids进行删除
    @DeleteMapping
    public R deleteByIds(Long[] ids){
        //验证参数是否异常
        if(null!=ids&&ids.length>0){
            //判断是否含有默认地址，如果有默认地址给出提示
            //创建条件查询条件
            LambdaQueryWrapper<AddressBook> qw = new LambdaQueryWrapper<>();
            qw.eq(AddressBook::getIsDefault,1).in(AddressBook::getId,ids);

            int count = addressBookService.count(qw);
            if(count>0){
                return R.fail("默认地址不能删除");
            }

            //如果不含默认地址，则可以进行删除
            boolean removeResult = addressBookService.removeByIds(Arrays.asList(ids));
            if(removeResult){
                return R.success("删除成功");
            }
            return R.fail("删除失败");
        }

        return R.fail("参数异常");
    }
}
