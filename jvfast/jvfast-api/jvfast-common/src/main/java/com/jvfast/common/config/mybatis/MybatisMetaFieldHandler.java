package com.jvfast.common.config.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jvfast.common.shiro.config.ShiroProperties;
import com.jvfast.common.shiro.service.JWTTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class MybatisMetaFieldHandler implements MetaObjectHandler {

    private final JWTTokenService JWTTokenService;
    private final ShiroProperties shiroProperties;

    /**
     * 插入的时候一些默认值,
     * 例如：     @TableField(fill = FieldFill.INSERT)
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 是否启用字段
        LocalDateTime now = LocalDateTime.now();
        Object status = this.getFieldValByName("status", metaObject);
        if (Objects.isNull(status)) {
            this.strictInsertFill(metaObject, "status", Boolean.class, true);
        }
        // 乐观锁version字段
        Object version = this.getFieldValByName("version", metaObject);
        if (Objects.isNull(version)) {
            this.strictInsertFill(metaObject, "version", Integer.class, 0);
        }
        // 创建时间
        Object gmtCreate = this.getFieldValByName("createTime", metaObject);
        if (Objects.isNull(gmtCreate)) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        }
        // 修改时间
        Object gmtModified = this.getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(gmtModified)) {
            this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        }

        Object createBy = this.getFieldValByName("createBy", metaObject);
        Object updateBy = this.getFieldValByName("updateBy", metaObject);
        Boolean shiroEnabled = shiroProperties.isEnable();
        String userId = "";
        boolean callUserId = false;
        if (ObjectUtil.isEmpty(createBy) && shiroEnabled) {
            userId = JWTTokenService.getUserId();
            if (StrUtil.isNotEmpty(userId)) {
                callUserId = true;
                this.strictInsertFill(metaObject, "createBy", Long.class, Long.valueOf(userId));
            }
        }
        if (ObjectUtil.isEmpty(updateBy) && shiroEnabled) {
            if (null == userId && false == callUserId) {
                userId = JWTTokenService.getUserId();
            }
            if (StrUtil.isNotEmpty(userId)) {
                this.strictInsertFill(metaObject, "updateBy", Long.class, Long.valueOf(userId));
            }
        }

    }

    /**
     * 更新的时候对应的一些默认值，
     * 例如：     @TableField(fill = FieldFill.INSERT_UPDATE)
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时候总是更新时间戳
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        // 如果没有设置的时候才更新才默认的值
        Object modifiedBy = this.getFieldValByName("updateBy", metaObject);
        Boolean shiroEnabled = shiroProperties.isEnable();
        String userId;
        if (ObjectUtil.isEmpty(modifiedBy) && shiroEnabled) {
            userId = JWTTokenService.getUserId();
            if (StrUtil.isNotEmpty(userId)) {
                this.strictUpdateFill(metaObject, "updateBy", Long.class, Long.valueOf(userId));
            }
        }
    }
}
