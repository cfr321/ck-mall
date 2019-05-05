package com.cako.item.web;

import com.cako.item.pojo.Brand;
import com.cako.item.service.BrandService;
import com.cako.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 查询页面信息
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param search
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "search", required = false) String search){

        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc,search));
    }

    /**
     * 新增品牌功能
     * @param brand
     * @param list
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> savaBrand(Brand brand, @RequestParam("cids") List<Long> list){
        brandService.savaBrand(brand,list);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updataBrand(Brand brand,@RequestParam("cids") List<Long> list){
        brandService.updataBrand(brand,list);
        return ResponseEntity.status((HttpStatus.OK)).build();
    }
    @DeleteMapping("{bid}")
    public ResponseEntity<Void> deleteBrand( @PathVariable("bid") Long bid){
        brandService.deleteBrand(bid);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        return  ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }
}
