package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    //添加分类，并检查名称是否重复
    boolean saveAndCheckName(Category category);

    //分页查询
    Page<Category> selectByPage(Integer currentPage, Integer pageSize);

    //更据id进行删除，并判断是否再
    boolean removeByIdWithoutUsered(Long id);
}