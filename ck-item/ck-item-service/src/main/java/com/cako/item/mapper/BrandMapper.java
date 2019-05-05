package com.cako.item.mapper;

import com.cako.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    int deleteByBid(@Param("bid") Long bid);

    @Select("SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid}")
    List<Long> selectCidByBid(@Param("bid") Long bid);

    @Select("select b.* from tb_brand b inner join tb_category_brand cb on b.id= cb.brand_id where cb.category_id= #{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);

}
