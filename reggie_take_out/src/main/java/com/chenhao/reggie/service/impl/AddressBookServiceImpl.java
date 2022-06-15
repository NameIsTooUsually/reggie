package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.AddressBook;
import com.chenhao.reggie.mapper.AddressBookMapper;
import com.chenhao.reggie.service.AddressBookService;
import com.chenhao.reggie.web.R;
import org.springframework.stereotype.Service;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/14 19:50
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Override
    public boolean updateDefault(AddressBook addressBook) {
        //根据id修改地址默认状态
        //先将列表中的默认id都清除
        AddressBook addressBook1 = new AddressBook();
        addressBook1.setIsDefault(0);
        boolean updateResult = this.update(addressBook1, null);
        if(!updateResult){
            return false;
        }

        //创建修改条件,修改目标地址的默认值
        LambdaQueryWrapper<AddressBook> qw = new LambdaQueryWrapper<>();
        addressBook.setIsDefault(1);
        qw.eq(AddressBook::getId,addressBook.getId());
        boolean result = this.update(addressBook, qw);

        return result;
    }
}
