package com.cako.item.pojo;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class SpuBo extends Spu {

   @Transient   //数据库扩展注释
   String cname;// 商品分类名称
   @Transient
   String bname;// 品牌名称
   @Transient
   SpuDetail spuDetail;// 商品详情
   @Transient
   List<Sku> skus;// sku列表
}