package com.jvfast.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Lists;
import com.jvfast.common.config.mybatis.BaseEntity;

import java.util.*;

public class MybatisPlusCodeGenerator {

    /**
     * 输入: auth_resource,auth_role,auth_role_resource,auth_user_role,sys_file_upload,sys_config,sys_log,sys_login_history,sys_news,sys_notice,sys_user
     */
    private static final String DRIVER_URL = "jdbc:mysql://127.0.0.1:3306/jvfast?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "syscorer";
    private static final String PASSWORD = "s6s@#@!L0ngh";
    // ------------------------修改下面两个字段: 产生的类存放的上级包路径
    private static final String OUTPUT_PACKAGE_NAME = "com.jvfast.module";
    // 产生的类的上级包名称, 注意: 表名称严格按照阿里规约,使用模块+功能进行命名,例如此处的表名必须为: demo_ +功能模块
    private static final String MODULE_NAME = "demo";
    private static final boolean OVERIDE_FILE = true;
    private static final boolean GENERATE_PERMISSION = false;
    // ------------------------修改上面两个字段: 产生的类的上级包路径
    // 产生类的import对应的路径设置
//    private static final String SUPER_CONTROLLER = "com.jvfast.common.config.mybatis.BaseController";
//    private static final String SUPER_SERVICE = "com.jvfast.common.config.mybatis.BaseService";
//    private static final String SUPER_SERVICE_IMPL = "com.jvfast.common.config.mybatis.BaseServiceImpl";
    private static final String SUPER_ENTITY = "com.jvfast.common.config.mybatis.BaseEntity";
    private static final String[] SUPER_ENTITY_COLUMNS = {"create_time", "create_by", "update_time", "update_by", "status", "version", "remark"};

    private static final String WEB_RESPONSE_CLASS_PATH = "com.jvfast.common.api.Result";
    private static final String WEB_RESPONSE_CODE_CLASS_PATH = "com.jvfast.common.api.ResultCode";
    private static final String QUERY_PARAM_PATH = "com.jvfast.common.param.QueryParam";
    private static final String ID_PARAM_PATH = "com.jvfast.common.param";
    private static final String LOG_ANNOTATION_PATH = "com.jvfast.common.annotation.Log";
    private static final String BUSINESS_TYPE_PATH = "com.jvfast.common.enums.BusinessTypeEnum";
    // 产生的import对应的包路径设置
    private static final String PARENT_CONTROLLER = "controller";
    private static final String PARENT_ENTITY = "model.entity";
    private static final String PARENT_MAPPER = "mapper";
    private static final String PARENT_SERVICE = "service";
    private static final String PARENT_SERVICE_IMPL = "service.impl";

    private static final String QUERY_PARAM_PACKAGE = OUTPUT_PACKAGE_NAME + StringPool.DOT + MODULE_NAME + StringPool.DOT + "model" + StringPool.DOT + "param";
    private static final String QUERY_VO_PACKAGE = OUTPUT_PACKAGE_NAME + StringPool.DOT + MODULE_NAME + StringPool.DOT + "model" + StringPool.DOT + "vo";
    private static final String MAPSTRACT_PACKAGE = OUTPUT_PACKAGE_NAME + StringPool.DOT + MODULE_NAME + StringPool.DOT + "converter";
//    private static final String EXCEPTION_PACKAGE = PARENT_PACKAGE + StringPool.DOT + "com.jvfast.common.exception";

    private static final boolean GENERATOR_CONTROLLER_METHODS = true;
    private static final boolean PAGE_LIST_ORDER = false;


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = FileUtil.getParent(MybatisPlusCodeGenerator.class.getClassLoader().getResource("").getPath(), 2);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(OVERIDE_FILE);
        gc.setOpen(false);
        // 是否在xml中添加二级缓存
        gc.setEnableCache(false);
        gc.setAuthor("Walter Hu");

        gc.setKotlin(false);
        gc.setSwagger2(true);

        gc.setActiveRecord(false);
        gc.setIdType(IdType.ASSIGN_ID);

        // xml中添加返回结果的基本类型
        gc.setBaseResultMap(false);
        // xmlmapping中添加基本的数据列
        gc.setBaseColumnList(false);
        // java8 时间映射，所有的时间映射被LocalDate,LocalDateTime类型
        gc.setDateType(DateType.TIME_PACK);

        gc.setEntityName("%s");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 修改默认引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 必须
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(DRIVER_URL);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USER_NAME);
        dsc.setPassword(PASSWORD);
        dsc.setTypeConvert((globalConfig, fieldType) -> {
            String t = fieldType.toLowerCase();
            if (t.contains("char")) {
                return DbColumnType.STRING;
            } else if (t.contains("bigint")) {
                return DbColumnType.LONG;
            } else if (t.contains("tinyint(1)")) {
                return DbColumnType.BOOLEAN;
            } else if (t.contains("int")) {
                return DbColumnType.INTEGER;
            } else if (t.contains("text")) {
                return DbColumnType.STRING;
            } else if (t.contains("bit")) {
                return DbColumnType.BOOLEAN;
            } else if (t.contains("decimal")) {
                return DbColumnType.BIG_DECIMAL;
            } else if (t.contains("clob")) {
                return DbColumnType.CLOB;
            } else if (t.contains("blob")) {
                // 2019-06-01 Walter:此处类型转换，将Blob类型转为byte[],否则mysql会获取的是null
                // see: https://github.com/baomidou/mybatis-plus/issues/670
                return DbColumnType.BYTE_ARRAY;
            } else if (t.contains("binary")) {
                return DbColumnType.BYTE_ARRAY;
            } else if (t.contains("float")) {
                return DbColumnType.FLOAT;
            } else if (t.contains("double")) {
                return DbColumnType.DOUBLE;
            } else if (t.contains("json") || t.contains("enum")) {
                return DbColumnType.STRING;
            } else if (t.contains("date") || t.contains("time") || t.contains("year")) {
                switch (globalConfig.getDateType()) {
                    case ONLY_DATE:
                        return DbColumnType.DATE;
                    case SQL_PACK:
                        switch (t) {
                            case "date":
                                return DbColumnType.DATE_SQL;
                            case "time":
                                return DbColumnType.TIME;
                            case "year":
                                return DbColumnType.DATE_SQL;
                            default:
                                return DbColumnType.TIMESTAMP;
                        }
                    case TIME_PACK:
                        switch (t) {
                            case "date":
                                return DbColumnType.LOCAL_DATE;
                            case "time":
                                return DbColumnType.LOCAL_TIME;
                            case "year":
                                return DbColumnType.YEAR;
                            default:
                                return DbColumnType.LOCAL_DATE_TIME;
                        }
                }
            }
            return DbColumnType.STRING;
        });
        mpg.setDataSource(dsc);
        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名是否大写
        strategy.setCapitalMode(false);
        strategy.setSkipView(true);
        // 表名到实体类的映射关系
        strategy.setNaming(NamingStrategy.underline_to_camel);
//        数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityColumnConstant(false);
        strategy.setEntityLombokModel(true);
        // 将boolean类型的字段去掉前面的is字段
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setEntitySerialVersionUID(true);
        strategy.setRestControllerStyle(true);
        //controller路径转为驼峰-形式
        strategy.setControllerMappingHyphenStyle(false);

        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
        // 乐观锁字段
        strategy.setVersionFieldName("version");
//        strategy.setLogicDeleteFieldName("active");
        // 默认字段
        strategy.setTableFillList(Lists.newArrayList(
                new TableFill("status", FieldFill.INSERT),
                new TableFill("version", FieldFill.INSERT),
                new TableFill("create_time", FieldFill.INSERT),
                new TableFill("update_time", FieldFill.INSERT_UPDATE),
                new TableFill("create_by", FieldFill.INSERT),
                new TableFill("update_by", FieldFill.INSERT_UPDATE)
        ));
        String tables = scanner("表名，多个表用英文逗号分割, 如果需要处理所有的数据库表请输入 a 即可(当前是否覆盖已产生的代码: "+OVERIDE_FILE+"):");
        String allTables = "a";
        if (!allTables.equals(tables)) {
            // 全部的表
            String[] parseTables = tables.split(",");
            strategy.setInclude(parseTables);
        }

        mpg.setStrategy(strategy);
        // 包名配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(OUTPUT_PACKAGE_NAME);
        pc.setModuleName(MODULE_NAME.toLowerCase());

        pc.setController(PARENT_CONTROLLER);
        pc.setEntity(PARENT_ENTITY);
        pc.setMapper(PARENT_MAPPER);
        pc.setService(PARENT_SERVICE);
        pc.setServiceImpl(PARENT_SERVICE_IMPL);

        mpg.setPackageInfo(pc);
        // 配置额外模板
        mpg.setCfg(getInjectionConfig(projectPath));
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        // 默认是采用velocity 模板的
        mpg.execute();
    }

    private static InjectionConfig getInjectionConfig(String projectPath) {
        String OUTPUT_PACKAGE_PATH = (OUTPUT_PACKAGE_NAME + StringPool.DOT + MODULE_NAME).replace(".", "/");
        // 自定义配置
        Map<String, Object> map = new HashMap<>(16);
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // 设置实体类中相关的变量属性
                // String serviceName = this.getConfig().getGlobalConfig().getServiceName();
                map.put("webResponsePath", WEB_RESPONSE_CLASS_PATH);
                map.put("webResponseCodePath", WEB_RESPONSE_CODE_CLASS_PATH);
                map.put("pageListOrder", PAGE_LIST_ORDER);
                map.put("idParamPath", ID_PARAM_PATH);
                map.put("queryParamPath", QUERY_PARAM_PATH);


                map.put("queryParamPackage", QUERY_PARAM_PACKAGE);
                map.put("queryVoPackage", QUERY_VO_PACKAGE);
                map.put("entityConverterPackage", MAPSTRACT_PACKAGE);

                map.put("logPackage", LOG_ANNOTATION_PATH);
                map.put("businessPackage", BUSINESS_TYPE_PATH);

                map.put("permissionEnabled", GENERATE_PERMISSION);
                map.put("newMenuId", IdWorker.getId());

                map.put("generatorStrategy", GENERATOR_CONTROLLER_METHODS);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        // xml文件位置
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String[] moduleNames = StrUtil.split(MODULE_NAME, ".");
                String apiModuleNames = ArrayUtil.join(moduleNames, "/");
                return projectPath + "/src/main/resources/mapper/" + apiModuleNames + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("/templates/AddParam.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/" + OUTPUT_PACKAGE_PATH + "/model/param/" + tableInfo.getEntityName() + "AddParam" + StringPool.DOT_JAVA;
            }
        });
        // 查询参数演示
        focList.add(new FileOutConfig("/templates/queryPageParam.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/" + OUTPUT_PACKAGE_PATH + "/model/param/" + tableInfo.getEntityName() + "QueryPageParam" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig("/templates/UpdateParam.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/" + OUTPUT_PACKAGE_PATH + "/model/param/" + tableInfo.getEntityName() + "UpdateParam" + StringPool.DOT_JAVA;
            }
        });
        //返回结果演示
        focList.add(new FileOutConfig("/templates/queryVo.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/" + OUTPUT_PACKAGE_PATH + "/model/vo/" + tableInfo.getEntityName() + "QueryVo" + StringPool.DOT_JAVA;
            }
        });
        // bean转换包
        focList.add(new FileOutConfig("/templates/entityConverter.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entityName = tableInfo.getEntityName().substring(0, 1).toLowerCase() + tableInfo.getEntityName().substring(1);
                map.put("entityName", entityName);
                return projectPath + "/src/main/java/" + OUTPUT_PACKAGE_PATH + "/converter/" + tableInfo.getEntityName() + "Converter" + StringPool.DOT_JAVA;
            }
        });
        // 前端页面相关
        focList.add(new FileOutConfig("/templates/frontend/api.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String functionName = tableInfo.getEntityName().toLowerCase();
                String[] tableNameArray = tableInfo.getName().split("_");
                if (tableNameArray.length > 0) {
                    functionName = tableNameArray[tableNameArray.length - 1].toLowerCase();
                }
                map.put("functionName", functionName);

                String[] moduleNames = StrUtil.split(MODULE_NAME, ".");
                String apiModuleNames = ArrayUtil.join(moduleNames, "/");
                return projectPath + "/src/main/resources/templates/src/api/" + apiModuleNames + "/" + functionName + ".js";
            }
        });
        focList.add(new FileOutConfig("/templates/frontend/api.uniapp.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String functionName = tableInfo.getEntityName().toLowerCase();
                String[] tableNameArray = tableInfo.getName().split("_");
                if (tableNameArray.length > 0) {
                    functionName = tableNameArray[tableNameArray.length - 1].toLowerCase();
                }
                map.put("functionName", functionName);

                String[] moduleNames = StrUtil.split(MODULE_NAME, ".");
                String apiModuleNames = ArrayUtil.join(moduleNames, "/");
                return projectPath + "/src/main/resources/templates/src/api/" + apiModuleNames + "/" + functionName + ".uniapp.js";
            }
        });
        focList.add(new FileOutConfig("/templates/frontend/store.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String functionName = tableInfo.getEntityName().toLowerCase();
                String[] tableNameArray = tableInfo.getName().split("_");
                if (tableNameArray.length > 0) {
                    functionName = tableNameArray[tableNameArray.length - 1].toLowerCase();
                }
                map.put("functionName", functionName);

                return projectPath + "/src/main/resources/templates/src/store/" +MODULE_NAME+"/"+ functionName + ".js";
            }
        });
        focList.add(new FileOutConfig("/templates/frontend/page.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String functionName = tableInfo.getEntityName().toLowerCase();
                String[] tableNameArray = tableInfo.getName().split("_");
                if (tableNameArray.length > 0) {
                    functionName = tableNameArray[tableNameArray.length - 1].toLowerCase();
                }
                map.put("functionName", functionName);
                String[] moduleNames = StrUtil.split(MODULE_NAME, ".");
                String apiModuleNames = ArrayUtil.join(moduleNames, "/");

                return projectPath + "/src/main/resources/templates/src/pages/" + apiModuleNames + "/" + functionName + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ": ");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String inputTip = scanner.next();
            if (StrUtil.isNotEmpty(inputTip)) {
                return inputTip;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "!");
    }

}
