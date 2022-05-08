package com.fxz.monitor.server.orm.repository;

import com.fxz.monitor.server.orm.entity.UserInfo;
import com.fxz.monitor.server.orm.repository.base.BaseRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author fxz
 */
public interface UserRepository extends BaseRepository<UserInfo> {

    /**
     * 当entity只有构造方法创建时，会因为select的字段数量小于构造方法的参数数量而报错 indexOutRangeException
     *
     * @param id
     * @return
     */
    @Select("select user_name,age,addr from user_info where id=#{id}")
    UserInfo findById(@Param("id") Long id);

    /**
     * mybatis 不支持方法名相同但参数或者返回值不同的方法，因为mapperStatementId=className+methodName
     *
     * @param id
     * @param deleted
     * @return
     */
    @Select("select * from user_info where id=#{id} and deleted=#{deleted}")
    UserInfo findById(@Param("id") Long id, @Param("deleted") Integer deleted);
}
