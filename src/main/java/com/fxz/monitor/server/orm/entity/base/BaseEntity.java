package com.fxz.monitor.server.orm.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author fxz
 */
@Data
public class BaseEntity {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer deleted = 0;
    private String creator;
    private Date createTime;
    private String updater;
    private Date updateTime;
}
