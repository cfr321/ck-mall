package com.cako.item.service;

import com.cako.enums.ExceptionEnum;
import com.cako.exception.CkException;
import com.cako.item.mapper.CategoryMapper;
import com.cako.item.pojo.Category;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类型Service
 */
@Service
public class CategotyService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid) {
        //查询条件，会用对象里面非空的条件作为查询条件
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categories=categoryMapper.select(category);
        //判断查询结果是否为空
        if(categories.isEmpty()){
          throw new CkException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return categories;
    }
    //根据品牌id查询品牌类别
    public List<Category> queryCategoryListByBid(Long bid) {
        List<Category> categories = categoryMapper.queryByBrandId(bid);
        if(categories.isEmpty()){
            throw new CkException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return categories;
    }
}
