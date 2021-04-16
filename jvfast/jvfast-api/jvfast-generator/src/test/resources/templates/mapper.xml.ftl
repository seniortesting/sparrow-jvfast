<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>
    </#if>
    <#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
        <#list table.fields as field>
        <#if field.keyFlag>
        <id column="${field.name}" property="${field.propertyName}"/>
        </#if>
        <#list table.commonFields as field>
        <result column="${field.name}" property="${field.propertyName}"/>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag>
        <result column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
        </#list>
    </resultMap>

    </#if>
    <#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list table.commonFields as field>
        ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>
    </#if>

    <#if table.name?contains("_")>
    <#assign tableShortName=table.name?substring(0,1)+table.name?substring(table.name?index_of("_")+1,table.name?index_of("_")+2)>
    <#else>
    <#assign tableShortName=table.name?substring(0,1)>
    </#if>
    <select id="page${entity}" resultType="${cfg.queryVoPackage}.${entity}QueryVo">
        SELECT
        <#list table.fields as field>
        ${tableShortName}.${field.name},
        </#list>
        <#list table.commonFields as field>
        ${tableShortName}.${field.name}<#if field_has_next>,</#if>
        </#list>
        FROM
        ${table.name} ${tableShortName}
        <where>
            ${tableShortName}.status=1
            <if test="query.keyword!=null and query.keyword!=''">
                <bind name="pattern" value="'%' + query.keyword + '%'"/>
                AND ${tableShortName}.name LIKE ${r"#{pattern}"}
            </if>
            <if test="query.startDate!=null and query.endDate!=null">
                AND DATE(${tableShortName}.create_time) BETWEEN ${r"#{query.startDate,jdbcType=DATE}"} AND ${r"#{query.endDate,jdbcType=DATE}"}
            </if>
        </where>
        ORDER BY ${tableShortName}.id DESC
    </select>

    <!-- ***************************** 以下为扩展接口 ****************************************************** -->

</mapper>
