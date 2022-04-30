package com.fxz.monitor.server.orm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fxz.monitor.server.orm.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author fxz
 */
@Data
/**
 * 如下两个注解会字节码增强增加全参数构造方法，因为lombok的构造方法参数顺序与mybatis
 * 构造方法创建对象参数不一致，会导致严重问题 1：类型不对报错 2：类型对参数错位值是错的
 @AllArgsConstructor
 @Builder
 */
@TableName("user_info")
public class UserInfo extends BaseEntity {

    /**
     * table:
     * CREATE TABLE
     * user_info
     * (
     * id INT(10) unsigned NOT NULL AUTO_INCREMENT,
     * name VARCHAR(24) NOT NULL,
     * age INT NOT NULL,
     * addr VARCHAR(256),
     * phone VARCHAR(20),
     * creator VARCHAR(24),
     * updater VARCHAR(24),
     * create_time TIMESTAMP NULL,
     * update_time TIMESTAMP NULL,
     * deleted TINYINT DEFAULT 0,
     * a_aa VARCHAR(128),
     * PRIMARY KEY (id)
     * )
     * ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
     */
    private String name;
    private Integer age;
    private String addr;
    private String phone;
    /**
     * for testing  mybatisplus 字段要规范，不然可能导致推断错误
     * 如 aaa 字段在BaseMapper.selectById 方法中的解析如下，字段错误直接报错
     * <p>
     * Preparing: SELECT id,name,age,addr,phone,aaa,deleted,creator,create_time,updater,update_time FROM user_info WHERE id=?
     */
//    private String aaa;
}


