package com.lagou.dao;

import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceCategoryMapper {
    /*
    查询所有资源分类
     */
    public List<ResourceCategory> findAllResourceCategory();

    /*
    添加资源分类
     */
    public void saveResourceCategory(ResourceCategory resourceCategory);

    /*
    回显资源分类
     */
    public ResourceCategory findResourceCategoryById(int reCaId);

    /*
    修改资源分类
     */
    public void updateResourceCategory(ResourceCategory resourceCategory);

    /*
    删除资源分类
     */
    public void deleteResourceCategory(int ceCaId);
}
