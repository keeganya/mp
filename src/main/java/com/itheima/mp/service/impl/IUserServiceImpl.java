package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author xiaohu
 * @Date 2024/11/22 9:16
 * @PackageName:com.itheima.mp.domain.service.impl
 * @ClassName: IUserServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Override
    public void deductBalance(Long id, Integer money) {
        // 1.查询用户
        User user = getById(id);
        // 2.校验用户状态
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态异常！");
        }
        // 3.检验余额是否充足
        if (user.getBalance() < money) {
            throw new RuntimeException("余额不足！");
        }
        // 4.扣减余额 update user set balance = balance - ?
        baseMapper.deductBalance(id,money);
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance) {
        return lambdaQuery().like(name != null,User::getUsername,name)
                .eq(status != null,User::getStatus,status)
                .ge(minBalance != null,User::getBalance,minBalance)
                .le(maxBalance != null,User::getBalance,maxBalance)
                .list();
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        // 1.构建分页条件
        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
        // 排序条件
        if (StrUtil.isNotBlank(query.getSortBy())) {
            page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        } else {
            page.addOrder(new OrderItem("update_time", false));
        }

        // 2.分页查询
        Page<User> p = lambdaQuery().like(query.getName() != null, User::getUsername, query.getName())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .page(page);

        // 3.封装VO结果
        PageDTO<UserVO> dto = new PageDTO<>();
        // 3.1 总条数
        dto.setTotal(p.getTotal());
        // 3.2 总页数
        dto.setPages(p.getPages());
        // 3.3 当前页数据
        List<User> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.emptyList());
            return dto;
        }

        // 拷贝user的vo
        List<UserVO> userVOS = BeanUtil.copyToList(records, UserVO.class);
        dto.setList(userVOS);
        return dto;
    }
}
