package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(Role role){
        List<Role> roleList = roleService.findAllRole(role);
        return new ResponseResult(true,200,"查询成功",roleList);
    }
    /*
    查询所有父子类菜单信息
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu(){
        //-1表示查询所有的父子级菜单
        List<Menu> list = menuService.findSubMenuListByPid(-1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList",list);
        return new ResponseResult(true,200,"查询菜单信息成功",map);
    }
     /*
    根据角色ID查询该角色关联的菜单信息ID
     */
     @RequestMapping("/findMenuByRoleId")
     public ResponseResult findMenuByRoleId(Integer roleId){
         List<Integer> list = roleService.findMenuByRoleId(roleId);
         return new ResponseResult(true,200,"查询角色关联的菜单成功",list);
     }
     /*
     为角色分配菜单
      */
     @RequestMapping("/RoleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVo roleMenuVo){
        roleService.roleContextMenu(roleMenuVo);
        return new ResponseResult(true,200,"分配菜单成功",null);
     }
     /*
     删除角色
      */
     @RequestMapping("/deleteRole")
     public ResponseResult deleteRole(Integer id){
         roleService.deleteRole(id);
         return new ResponseResult(true,200,"删除角色成功",null);
     }
}
