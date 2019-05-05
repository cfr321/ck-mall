package com.cako.item.service;

import com.cako.enums.ExceptionEnum;
import com.cako.exception.CkException;
import com.cako.item.mapper.SpecGroupMapper;
import com.cako.item.mapper.SpecParamMapper;
import com.cako.item.pojo.SpecGroup;
import com.cako.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 商品参数组的增删改查
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> lists=specGroupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(lists)){
            //没有查到
            throw new CkException(ExceptionEnum.GROUP_NOT_FOND);
        }
        return lists;
    }
    public void updateGroup(SpecGroup specGroup) {
        if(specGroup.getName()==null){
            throw new CkException(ExceptionEnum.GROUP_SAVE_ERROR);
        }
        int i = specGroupMapper.updateByPrimaryKey(specGroup);
        if(i!=1){
            throw new CkException(ExceptionEnum.GROUP_UPDATE_ERROR);
        }
    }
    public void saveGroup(SpecGroup specGroup) {
        if(specGroup.getName()==null){
            throw new CkException(ExceptionEnum.GROUP_SAVE_ERROR);
        }
        specGroup.setId(null);
        int insert = specGroupMapper.insert(specGroup);
        if(insert!=1){
            throw new CkException(ExceptionEnum.GROUP_SAVE_ERROR);
        }
    }
    public void deledeGroup(Long gid) {
        int i = specGroupMapper.deleteByPrimaryKey(gid);
        if(i!=1){
            throw new CkException(ExceptionEnum.GROUP_DELETE_ERROR);
        }
    }


    /**
     * 商品分组参数细节的增删该查
     * @param gid
     * @return
     */
    public List<SpecParam> queryParamList(Long gid,Long cid,Boolean searching) {
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(list)){
            throw new CkException(ExceptionEnum.PARAM_NOT_FOND);
        }
        return list;
    }
    public void saveParam(SpecParam specParam) {
        if(specParam.getName()==null){
            throw new CkException(ExceptionEnum.PARAM_SAVE_ERROR);
        }
        specParam.setId(null);
        int insert = specParamMapper.insert(specParam);
        if(insert!=1){
            throw new CkException(ExceptionEnum.PARAM_SAVE_ERROR);
        }
    }
    public void deleteParam(Long pid) {
        int i = specParamMapper.deleteByPrimaryKey(pid);
        if(i!=1){
            throw new CkException(ExceptionEnum.PARAM_DELETE_ERROR);
        }
    }
    public void updateParam(SpecParam specParam) {
        if(specParam.getName()==null){
            throw new CkException(ExceptionEnum.PARAM_SAVE_ERROR);
        }
        int i = specParamMapper.updateByPrimaryKey(specParam);
        if(i!=1){
            throw new CkException(ExceptionEnum.PARAM_UPDATE_ERROR);
        }
    }
}
