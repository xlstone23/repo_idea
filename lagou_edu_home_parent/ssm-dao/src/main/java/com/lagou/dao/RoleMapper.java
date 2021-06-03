package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;

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

}
