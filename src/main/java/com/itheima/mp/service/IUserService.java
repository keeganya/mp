package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;

import java.util.List;

/**
 * @Author xiaohu
 * @Date 2024/11/22 9:14
 * @PackageName:com.itheima.mp.domain.service
 * @ClassName: IUserService
 * @Description: TODO
 * @Version 1.0
 */
public interface IUserService extends IService<User> {
    void deductBalance(Long id, Integer money);

    List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance);

    PageDTO<UserVO> queryUsersPage(UserQuery query);
}
