package com.cako.item.web;

import com.cako.item.pojo.SpecGroup;
import com.cako.item.pojo.SpecParam;
import com.cako.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specService;

    /**
     * 根据分类id 查询 所有的组
     * 后面1、2、3、4,分别组的 查、增、删、改。
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }
    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup specGroup){
        specService.saveGroup(specGroup);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @DeleteMapping("group/{gid}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("gid") Long gid){
        specService.deledeGroup(gid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup){
        specService.updateGroup(specGroup);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    /**
     * 根据组id查询 组下的所有参数
     * 后面1、2、3、4,分别为参数的 查、增、删、改。
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamByGid(@RequestParam("gid") Long gid){
        return ResponseEntity.ok(specService.queryParamByGid(gid));
    }
    @PostMapping("param")
    public ResponseEntity<Void>  saveParam(@RequestBody SpecParam specParam){
        specService.saveParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping( "param/{pid}")
    public ResponseEntity<Void> deleteParam(@PathVariable("pid") Long pid){
        specService.deleteParam(pid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam){
        specService.updateParam(specParam);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
