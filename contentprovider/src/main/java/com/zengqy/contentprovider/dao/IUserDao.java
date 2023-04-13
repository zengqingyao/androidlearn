package com.zengqy.contentprovider.dao;

import com.zengqy.contentprovider.pojo.User;

import java.util.List;

/**
 * @包名: com.zengqy.contentprovider.dao
 * @USER: zengqy
 * @DATE: 2022/4/8 13:43
 * @描述: 数据库的接口层
 */
public interface IUserDao {

    /**
     * 添加用户
     * @param user
     * @return
     */
    long addUser(User user);


    /**
     * 根据ID删除用户
     * @param id
     * @return
     */
    int delUserById(int id);


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);


    /**
     * 查询用户记录
     * @param id
     * @return
     */
    User getUserById(int id);

    /**
     * 获取所有用户
     * @return
     */
    List<User> listAllUser();
}
