package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {
    void updateBalanceByIds(@Param(Constants.WRAPPER) UpdateWrapper<User> wrapper, @Param("amount") int amount);

    @Update("update user set balance = balance - #{money} where id = #{id}")
    void deductBalance(@Param("id")Long id, @Param("money")Integer money);

    /** 继承 mybatis-plus的 Mapper
     * 自定义的单表查询方法可以去掉
     *  对应的xml文件中的sql也可以删掉
    * */
    /*
    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User queryUserById(@Param("id") Long id);

    List<User> queryUserByIds(@Param("ids") List<Long> ids);
    */
}
