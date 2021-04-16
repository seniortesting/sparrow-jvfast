package com.jvfast.module.monitor.model.vo;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.util.List;

@Data
public class SystemInfoQueryVo {


    private List<SysFile> sysFile;
    private CPU cpu;
    private JVM jvm;
    private Memory memory;
    private SysInfo sysInfo;

    @Data
    public static class SysFile {
        /**
         * 盘符路径
         */
        private String dirName;

        /**
         * 盘符类型
         */
        private String sysTypeName;

        /**
         * 文件类型
         */
        private String typeName;

        /**
         * 总大小
         */
        private String total;

        /**
         * 剩余大小
         */
        private String free;

        /**
         * 已经使用量
         */
        private String used;

        /**
         * 资源的使用率
         */
        private double usage;
    }

    @Data
    public static class CPU {
        /**
         * 核心数
         */
        private int cpuNum;

        /**
         * CPU总的使用率
         */
        private double total;

        /**
         * CPU系统使用率
         */
        private double sys;

        /**
         * CPU用户使用率
         */
        private double used;

        /**
         * CPU当前等待率
         */
        private double wait;

        /**
         * CPU当前空闲率
         */
        private double free;

        public double getTotal() {
            return NumberUtil.round(NumberUtil.mul(total, 100), 2).doubleValue();
        }

        public double getSys() {
            return NumberUtil.round(NumberUtil.mul(sys / total, 100), 2).doubleValue();
        }

        public double getUsed() {
            return NumberUtil.round(NumberUtil.mul(used / total, 100), 2).doubleValue();
        }

        public double getWait() {
            return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2).doubleValue();
        }

        public double getFree() {
            return NumberUtil.round(NumberUtil.mul(free / total, 100), 2).doubleValue();
        }


    }

    @Data
    public static class JVM {
        /**
         * 当前JVM占用的内存总数(M)
         */
        private double total;

        /**
         * JVM最大可用内存总数(M)
         */
        private double max;

        /**
         * JVM空闲内存(M)
         */
        private double free;

        /**
         * JDK版本
         */
        private String version;

        /**
         * JDK路径
         */
        private String home;

        public double getTotal() {
            return NumberUtil.div(total, (1024 * 1024), 2);
        }

        public double getMax() {
            return NumberUtil.div(max, (1024 * 1024), 2);
        }

        public double getFree() {
            return NumberUtil.div(free, (1024 * 1024), 2);
        }

        public double getUsed() {
            return NumberUtil.div(total - free, (1024 * 1024), 2);
        }

        public double getUsage() {
            return NumberUtil.mul(NumberUtil.div(total - free, total, 4), 100);
        }
    }

    @Data
    public static class Memory {
        /**
         * 内存总量
         */
        private double total;

        private double max;
        /**
         * 已用内存
         */
        private double used;

        /**
         * 剩余内存
         */
        private double free;

        public double getTotal() {
            return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
        }

        public double getFree() {
            return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
        }

        public double getUsed() {
            return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
        }

        public double getUsage() {
            return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
        }
    }

    @Data
    public static class SysInfo {
        /**
         * 服务器名称
         */
        private String computerName;

        /**
         * 服务器Ip
         */
        private String computerIp;

        /**
         * 项目路径
         */
        private String userDir;

        /**
         * 操作系统
         */
        private String osName;

        /**
         * 系统架构
         */
        private String osArch;
    }
}
