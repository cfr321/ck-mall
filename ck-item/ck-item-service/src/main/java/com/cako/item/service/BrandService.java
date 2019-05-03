package com.cako.item.service;

import com.cako.enums.ExceptionEnum;
import com.cako.exception.CkException;
import com.cako.item.mapper.BrandMapper;
import com.cako.item.pojo.Brand;
import com.cako.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;
    //根据页面信息查询
    public PageResult<Brand> queryBrandByPage
            (Integer page, Integer rows, String sortBy, Boolean desc, String search) {
        //分页
        PageHelper.startPage(page,rows);
        /**
         * where 'name' like "% x %" or letter=='x'
         * order by id desc
         */
        Example example = new Example(Brand.class);
        //过滤
        if(StringUtils.isNoneBlank(search)){
            //过滤条件
            example.createCriteria().orLike("name","%"+search+"%")
                    .orEqualTo("letter",search.toUpperCase());
        }
        //排序
        if(StringUtils.isNoneBlank(sortBy)){
            String oderByClause=sortBy+(desc? " DESC": " ASC");
            example.setOrderByClause(oderByClause);
        }
        //查询
        List<Brand> list=brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new CkException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info=new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);
    }
    //新增商品类别
    @Transactional  //加上事务
    public void savaBrand(Brand brand, List<Long> list) {
        //新增品牌信息
        brand.setId(null);
        int insert = brandMapper.insert(brand);
        if(insert!=1){
            throw new CkException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表
        list.forEach((cid)->{
            int i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(i!=1){
                throw new CkException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        });
    }
    //跟新品牌信息
    @Transactional
    public void updataBrand(Brand brand,List<Long> list1) {
        int i = brandMapper.updateByPrimaryKey(brand);
        if(i!=1){
            throw new CkException(ExceptionEnum.UPDATA_BRAND_ERROR);
        }
        //查询出品牌对应的类别
        List<Long> list2 = brandMapper.selectCidByBid(brand.getId());
        boolean flag = true;
        // 比较两个List是否相同，校验是否修改了类别
        if (list1.size() != list2.size()) {
            flag=false;
        }else {
            Long[] arr1 = list1.toArray(new Long[]{});
            Long[] arr2 = list2.toArray(new Long[]{});
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            flag=Arrays.equals(arr1, arr2);
        }
        //如果修改了类别直接删掉以前的再重新插入新的，因为不知道改了少了还是改多了，不好用updata
        if(!flag){
            brandMapper.deleteByBid(brand.getId());
            list1.forEach((cid)->{
                int i1 = brandMapper.insertCategoryBrand(cid, brand.getId());
                if(i1!=1){
                    throw new CkException(ExceptionEnum.UPDATA_BRAND_ERROR);
                }
            });
        }
    }
    //根据品牌id删除品牌和分类
    @Transactional
    public void deleteBrand(Long bid) {
        int i = brandMapper.deleteByPrimaryKey(bid);
        if(i!=1){
            throw new CkException(ExceptionEnum.DELETE_BRAND_ERROR);
        }
        int i1 = brandMapper.deleteByBid(bid);
        if(i1<1){
            throw new CkException(ExceptionEnum.DELETE_BRAND_ERROR);
        }
    }
}
