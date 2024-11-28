package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xiaohu
 * @Date 2024/11/22 9:49
 * @PackageName:com.itheima.mp.controller
 * @ClassName: UserController
 * @Description: TODO
 * @Version 1.0
 */
@Api(tags = "用户管理接口")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userDTO) {
        // 1.把DTO拷贝到PO
        User user = BeanUtil.copyProperties(userDTO, User.class);
        // 2.新增
        userService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @ApiOperation("根据id查询用户接口")
    @GetMapping("{id}")
    public UserVO queryUserbyId(@ApiParam("用户id") @PathVariable("id") Long id) {
        User user = userService.getById(id);
        return BeanUtil.copyProperties(user,UserVO.class);
    }

    @ApiOperation("根据id批量查询用户接口")
    @GetMapping
    public List<UserVO> queryUserbyIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users,UserVO.class);
    }

    @ApiOperation("扣减用户余额接口")
    @PutMapping("{id}/deduction/{money}")
    public void deductBalance(@ApiParam("用户id") @PathVariable("id") Long id,
                              @ApiParam("扣减金额") @PathVariable("money") Integer money) {
        // 自定义service方法
        userService.deductBalance(id,money);
    }

    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> queryUsers(UserQuery query) {
        // 1.查询用户PO
        List<User> users = userService.queryUsers(query.getName(),query.getStatus(),query.getMinBalance(),query.getMaxBalance());
        // 2.把PO拷贝到VO
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @ApiOperation("根据复杂条件分页查询用户接口")
    @GetMapping("/page")
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        return userService.queryUsersPage(query);
    }
}
