package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> list = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public User login(User user) throws Exception {
        //1.调用mapper方法
        User user1 = userMapper.login(user);
        if(user1 !=null && Md5.verify(user.getPassword(),"lagou",user1.getPassword())){
            return user1;
        }else{
            return null;
        }
    }

    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        List<Role> list = userMapper.findUserRelationRoleById(id);
        return list;
    }

    @Override
    public void userContextRole(UserVo userVo) {
        //1.根据用户ID清空中间表关系
        userMapper.deleteUserContextRole(userVo.getUserId());
        //2.再重新建立关联关系
        for (Integer roleId : userVo.getRoleIdList()) {
            //封装数据
            User_Role_relation user_role_relation=new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);
            Date date=new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");
            userMapper.userContextRole(user_role_relation);
        }
    }

    @Override
    public ResponseResult getUserPermissions(Integer userId) {
        //1.获取当前用户拥有的角色
        List<Role> roleId = userMapper.findUserRelationRoleById(userId);
        //2.获取角色Id，保存到List集合中
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roleId) {
            roleIds.add(role.getId());
        }
        //3.根据角色ID查询父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);

        //4.查询封装父菜单关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setMenuList(subMenu);
        }
        //5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);
        //6.封装数据并返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("menuList",parentMenu);
        map.put("resourceList",resourceList);
        return new ResponseResult(true,200," 获取用户权限，进行菜单动态展示成功",map);
    }
}
