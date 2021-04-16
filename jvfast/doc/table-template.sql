--
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

--
--  创建新表的模板，所有的表创建需要已此为模板
-- Create table 新表名称
--
CREATE TABLE 新表名称
(
    -- 必须字段 id, uuid, active,version, gmt_create,gmt_modified, create_by, modified_by
    id          BIGINT(20) UNSIGNED NOT NULL COMMENT '记录唯一标识id',
    -- 此处添加其他的字段

    -- 以下为必须添加的额外保留字段
    status      TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '对应记录是否可用，1可用，0不可用',
    remark      VARCHAR(500)                 DEFAULT '' COMMENT '对应记录备注',
    version     INT(20) UNSIGNED    NOT NULL DEFAULT 0 COMMENT '对应记录的修订版本号,乐观锁',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '对应记录的创建时间',
    create_by   VARCHAR(64)                  DEFAULT '' COMMENT '对应记录的创建者',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '对应记录的最后修改时间',
    update_by   VARCHAR(64)                  DEFAULT '' COMMENT '对应记录的最后修改者',
    PRIMARY KEY (id),
    UNIQUE INDEX UK_primary_id (id)
)
    ENGINE = INNODB,
    CHARACTER SET utf8mb4,
    COLLATE utf8mb4_general_ci,
    COMMENT = '新表说明';
