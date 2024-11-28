package com.itheima.mp.domain.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xiaohu
 * @Date 2024/11/22 9:20
 * @PackageName:com.itheima.mp.domain.service
 * @ClassName: IUserServiceTest
 * @Description: TODO
 * @Version 1.0
 */
@SpringBootTest
class IUserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
    }

    @Test
    void testQuery() {
        List<User> users = userService.listByIds(List.of(1L, 2L, 5L));
        users.forEach(System.out::println);
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("" + (138222221L + i));
        user.setBalance(2000);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }

    /**
     * 插入10w条数据
    * */
    @Test
    void testSaveOneByOne() {
        long b = System.currentTimeMillis();
        for (int i = 1; i < 100000; i++) {
            userService.save(buildUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    /**
     * 批量操作插入10w条数据
     *  每次批量插入1000条 就提交，插入100次即10万条
     *  rewriteBatchedStatements=true
    * */
    @Test
    void testSavebatch() {
        // 1.准备一个容量为1000的集合
        List<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            // 2.添加一个user
            list.add(buildUser(i));
            // 3.每1000条批量插入一次
            if (i % 1000 == 0) {
                userService.saveBatch(list);
                // 4.清空集合，准备下一批数据
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    /**
     * 分页查询
    * */
    @Test
    void testPageQuery() {
        int pageNo = 1,pageSize = 2;
        // 1.准备分页条件
        // 1.1 分页条件
        Page<User> page = Page.of(pageNo, pageSize);
        // 1.2 排序条件
        page.addOrder(new OrderItem("balance",true));
        page.addOrder(new OrderItem("id",true));

        // 2.分页查询
        Page<User> p = userService.page(page);

        // 3.解析
        long total = p.getTotal(); // 数据数量
        System.out.println("total = " + total);
        long pages = p.getPages(); // 页数
        System.out.println("pages = " + pages);
        List<User> users = p.getRecords(); // 数据集
        users.forEach(System.out::println);
    }
}