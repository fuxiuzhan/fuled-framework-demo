package com.fxz.monitor.server.orm.repository.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxz.monitor.server.orm.entity.base.BaseEntity;

/**
 * @author fxz
 */
public interface BaseRepository<T extends BaseEntity> extends BaseMapper<T> {
}
