package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.*;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findAllRole(@RequestBody Role role) {
        List<Role> list = roleMapper.findAllRole(role);
        return list;
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        List<Integer> list = roleMapper.findMenuByRoleId(roleId);
        return list;
    }

    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        //1.清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());
        //2.为角色分配菜单
        for (Integer mid : roleMenuVo.getMenuIdList()) {
            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());
            role_menu_relation.setCreatedTime(new Date());
            role_menu_relation.setUpdatedTime(new Date());
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");
            roleMapper.roleContextMenu(role_menu_relation);
        }

    }

    @Override
    public void deleteRole(Integer roleId) {
        //根据roleId清空中间表
        roleMapper.deleteRoleContextMenu(roleId);
        roleMapper.deleteRole(roleId);
    }

    @Override
    public List<ResourceCategory> findResourceListByRoleId(int roleId) {
        List<ResourceCategory> resourceCategoryByRoleId = roleMapper.findResourceCategoryByRoleId(roleId);
        List<Resource> resourceByRoleId = roleMapper.findResourceByRoleId(roleId);
        List<ResourceCategory> list2=new ArrayList<>();
        for (ResourceCategory resourceCategory1 : resourceCategoryByRoleId) {
            ResourceCategory resourceCategory=new ResourceCategory();
            resourceCategory.setId(resourceCategory1.getId());
            resourceCategory.setName(resourceCategory1.getName());
            resourceCategory.setSort(resourceCategory1.getSort());
            resourceCategory.setCreatedTime(resourceCategory1.getCreatedTime());
            resourceCategory.setUpdatedTime(resourceCategory1.getUpdatedTime());
            resourceCategory.setCreatedBy(resourceCategory1.getCreatedBy());
            resourceCategory.setUpdatedBy(resourceCategory1.getUpdatedBy());
            List<Resource> list = new ArrayList<>();
            for (Resource r1 : resourceByRoleId) {
                if(resourceCategory1.getId()==r1.getCategoryId()){
                    Resource resource=new Resource();
                    resource.setId(r1.getId());
                    resource.setCategoryId(r1.getCategoryId());
                    resource.setCreatedBy(r1.getCreatedBy());
                    resource.setCreatedTime(r1.getCreatedTime());
                    resource.setDescription(r1.getDescription());
                    resource.setName(r1.getName());
                    resource.setUpdatedBy(r1.getUpdatedBy());
                    resource.setUpdatedTime(r1.getUpdatedTime());
                    resource.setUrl(r1.getUrl());
                    list.add(resource);
                }
                resourceCategory.setResourceList(list);
            }
            list2.add(resourceCategory);
        }
        return list2;
    }

    @Override
    public void roleContextResource(RoleResourceVo roleResourceVo) {
        roleMapper.deleteRoleResourceRelation(roleResourceVo.getRoleId());
        for (Integer roleId : roleResourceVo.getResourceIdList()) {
            RoleResourceRelation rrr=new RoleResourceRelation();
            rrr.setRoleId(roleResourceVo.getRoleId());
            rrr.setResourceId(roleId);
            Date date=new Date();
            rrr.setCreatedTime(date);
            rrr.setUpdatedTime(date);
            rrr.setCreatedBy("system");
            rrr.setUpdatedBy("system");
            roleMapper.saveRoleResourceRelation(rrr);
        }
    }


}
