package com.cako.item.service;

import com.cako.enums.ExceptionEnum;
import com.cako.exception.CkException;
import com.cako.item.mapper.*;
import com.cako.item.pojo.*;
import com.cako.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper detailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;
    public PageResult<SpuBo> querySpuByPageAndSort(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page, Math.min(rows,50));

        Example example=new Example(Spu.class);
        // 是否过滤上下架
        if (saleable != null) {
            example.createCriteria().orEqualTo("saleable", saleable);
        }
        // 是否模糊查询
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("title", "%" + key + "%");
        }
        List<Spu> list = spuMapper.selectByExample(example);

        List<SpuBo> pageInfo=list.stream().map(spu -> {
            // 2、把spu变为 spuBo
            SpuBo spuBo = new SpuBo();
            // 属性拷贝
            BeanUtils.copyProperties(spu, spuBo);

            // 3、查询spu的商品分类名称,要查三级分类
            List<String> names =categoryService.queryNameByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            // 将分类名称拼接后存入
            spuBo.setCname(StringUtils.join(names, "/"));

            // 4、查询spu的品牌名称
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            return spuBo;
        }).collect(Collectors.toList());
        //解析分页结果
        PageInfo<SpuBo> info=new PageInfo<>(pageInfo);
        return new PageResult<>(info.getTotal(),pageInfo);
    }
    @Transactional
    public void saveGoods(SpuBo spu) {
        //新增spu
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int insert = spuMapper.insert(spu);
        if(insert!=1){
            throw new CkException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //新增detail
        SpuDetail detail=spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        detailMapper.insert(detail);
        //定义一个库存的集合
        List<Stock> stocks=new ArrayList<>();
        //新增sku
        List<Sku> skuList=spu.getSkus();
        skuList.forEach((sku)->{
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            int insert1 = skuMapper.insert(sku);
            if(insert1!=1){
                throw new CkException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        });
        //新增库存
        stockMapper.insertList(stocks);
    }

    public SpuDetail queryDetailById(Long spuId) {
        SpuDetail detail = detailMapper.selectByPrimaryKey(spuId);
        if (detail==null){
            throw new CkException(ExceptionEnum.GOODS_DETAIL_NOT_FOUD);
        }
        return detail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku=new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skus)){
            throw new CkException(ExceptionEnum.GOODS_SKU_NOT_FOUD);
        }
        //查询库存
        List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stocks = stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stocks)){
            throw new CkException(ExceptionEnum.GOODS_STOCK_NOT_FOND);
        }
        //以商品skuid 为key，库存为value的map
        Map<Long, Integer> stocksMap =
                stocks.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach(s ->s.setStock(stocksMap.get(s.getId())));
        return skus;
    }
}
