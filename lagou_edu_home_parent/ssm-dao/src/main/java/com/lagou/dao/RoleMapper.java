package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface RoleMapper {
    /*
    查询所有角色&条件查询
     */
    public List<Role> findAllRole(Role role);

    /*
    根据角色ID查询该角色关联的菜单信息ID
     */
    public List<Integer> findMenuByRoleId(Integer roleId);

    /*
    根据RoleId清空中间表关系
     */
    public void deleteRoleContextMenu(Integer rid);

    /*
    为角色分配关系
     */
    public void roleContextMenu(Role_menu_relation role_menu_relation);

    /*
    删除角色
     */
    public void deleteRole(Integer roleId);

    /*
    - 方法1: 查询当前角色拥有的资源分类信息
    - 方法2: 查询当前角色拥有的资源信息
    */
    public List<ResourceCategory> findResourceCategoryByRoleId(int roleId);

    public List<Resource> findResourceByRoleId(int roleId);
    /*
    为角色分配菜单
    1.方法1 根据角色ID 删除角色与资源的关联关系(操作中中间表 role_resource_relation)
    2.方法2 为角色分配资源, 删除完成后 ,插入最新的关联关系
     */
    public void deleteRoleResourceRelation(int roleId);
    public void saveRoleResourceRelation(RoleResourceRelation roleResourceRelation);

}
