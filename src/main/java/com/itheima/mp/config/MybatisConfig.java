package com.itheima.mp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xiaohu
 * @Date 2024/11/22 16:59
 * @PackageName:com.itheima.mp.config
 * @ClassName: MybatisConfig
 * @Description: mybatisPlus的拦截器（这里添加了分页拦截器）
 * @Version 1.0
 */
@Configuration
public class MybatisConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // mybatisPlus拦截器对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 1.创建分页拦截器对象
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(1000L);// 限制1000条

        // 2.mybatisPlus拦截器添加分页拦截器插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;

    }
}
