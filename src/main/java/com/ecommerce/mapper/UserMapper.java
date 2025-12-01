package com.ecommerce.mapper;

import com.ecommerce.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Mapper
public interface UserMapper {

    /**
     * 根据ID查询用户
     */
    User selectUserById(@Param("id") Long id);

    /**
     * 根据邮箱查询用户
     */
    User selectUserByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     */
    User selectUserByPhone(@Param("phone") String phone);

    /**
     * 插入用户
     */
    int insertUser(User user);

    /**
     * 更新用户
     */
    int updateUser(User user);

    /**
     * 根据用户名查询用户
     */
    User selectUserByUsername(@Param("username") String username);

    /**
     * 检查邮箱是否存在
     */
    int checkEmailExists(@Param("email") String email);

    /**
     * 检查手机号是否存在
     */
    int checkPhoneExists(@Param("phone") String phone);

    /**
     * 检查用户名是否存在
     */
    int checkUsernameExists(@Param("username") String username);

    /**
     * 更新用户密码
     */
    int updatePassword(@Param("userId") Long userId, @Param("password") String password, @Param("updatedTime") java.time.LocalDateTime updatedTime);

    /**
     * 更新用户登录信息
     */
    int updateUserLoginInfo(User user);
}