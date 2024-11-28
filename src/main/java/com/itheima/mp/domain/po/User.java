package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  1.类名驼峰转下划线作为表名
 *  2.名为id的字段作为主键
 *  3.变量名驼峰转下划线作为表的字段名
 *
 *  @TableName:当实体类名和表名不一致，用来指定表名
 *              @TableName("tb_user")
 *
 *  @TableId:当实体类中的主键名和表的主键名不一致，用来指定表中的主键字段信息
 *              @TableId(value = "id",type = IdType.INPUT)
 *              idType枚举：
 *                  1.AUTO:数据库自增长
 *                  2.INPUT:通过set方法自行输入
 *                  3.ASSIGN_ID:分配ID 接口IdentifierGenerator的方法nextId来生成id
 *                                    默认实现类为DefaultidentifierGenerator雪花算法
 *
 *  @TableField：用来指定表中的普通字段信息
 *              1.成员变量与数据库字段名不一致
 *              2.成员变量名以is开头，【且是布尔值】 【isMarried】 和 【is_married】 会忽略isMarried的is，认为字段名为married
 *              3.成员变量与数据库关键字冲突; 成员变量叫 order,是数据库关键字；则@TableField("`order`")
 *              4.成员变量不是数据库字段；@TableField(exist = false)
* */
@Data
public class User {

    /**
     * 用户id
     */
//    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 详细信息
     */
    private String info;

    /**
     * 使用状态（1正常 2冻结）
     */
    private Integer status;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
