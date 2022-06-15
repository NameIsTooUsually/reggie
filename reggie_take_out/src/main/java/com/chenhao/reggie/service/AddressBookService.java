package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.AddressBook;

public interface AddressBookService extends IService<AddressBook> {
    boolean updateDefault(AddressBook addressBook);
}
