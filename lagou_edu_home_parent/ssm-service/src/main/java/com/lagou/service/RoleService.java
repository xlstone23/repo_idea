package com.lagou.service;

import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

public interface RoleService {
    /*
   查询所有角色&条件查询
    */
    public List<Role> findAllRole(Role role);
    /*
   根据角色ID查询该角色关联的菜单信息ID
    */
    public List<Integer> findMenuByRoleId(Integer roleId);
    /*
    为角色分配关系
     */
    public void roleContextMenu(RoleMenuVo roleMenuVo);
    /*
    删除角色
     */
    public void deleteRole(Integer roleId);
}
