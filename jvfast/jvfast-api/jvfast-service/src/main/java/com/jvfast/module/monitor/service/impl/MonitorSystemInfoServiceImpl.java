package com.jvfast.module.monitor.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.HostInfo;
import com.jvfast.module.monitor.model.vo.SystemInfoQueryVo;
import com.jvfast.module.monitor.service.MonitorSystemInfoService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Service
public class MonitorSystemInfoServiceImpl implements MonitorSystemInfoService {

    private static final int OSHI_WAIT_SECOND = 1000;

    @Override
    public SystemInfoQueryVo getSystemInfo() {
        SystemInfoQueryVo systemInfoQueryVo = new SystemInfoQueryVo();

        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        List<SystemInfoQueryVo.SysFile> sysFiles = getSysFiles(operatingSystem);
        systemInfoQueryVo.setSysFile(sysFiles);

        GlobalMemory memory = hardware.getMemory();
        SystemInfoQueryVo.Memory memInfo = getMemInfo(memory);
        systemInfoQueryVo.setMemory(memInfo);

        CentralProcessor processor = hardware.getProcessor();
        SystemInfoQueryVo.CPU cpuInfo = getCpuInfo(processor);
        systemInfoQueryVo.setCpu(cpuInfo);

        SystemInfoQueryVo.SysInfo sysInfo = getSysInfo();
        systemInfoQueryVo.setSysInfo(sysInfo);

        SystemInfoQueryVo.JVM jvmInfo = null;
        try {
            jvmInfo = getJvmInfo();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        systemInfoQueryVo.setJvm(jvmInfo);

        return systemInfoQueryVo;
    }

    /**
     * 设置磁盘信息
     */
    private List<SystemInfoQueryVo.SysFile> getSysFiles(OperatingSystem os) {
        List<SystemInfoQueryVo.SysFile> sysFiles = new LinkedList<SystemInfoQueryVo.SysFile>();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SystemInfoQueryVo.SysFile sysFile = new SystemInfoQueryVo.SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            double usedRate = NumberUtil.div(used, total, 4);
            double usage = NumberUtil.mul(usedRate, 100);
            sysFile.setUsage(usage);
            sysFiles.add(sysFile);
        }
        return sysFiles;
    }

    /**
     * 设置CPU信息
     */
    private SystemInfoQueryVo.CPU getCpuInfo(CentralProcessor processor) {
        SystemInfoQueryVo.CPU cpu = new SystemInfoQueryVo.CPU();
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
        return cpu;
    }

    /**
     * 设置内存信息
     */
    private SystemInfoQueryVo.Memory getMemInfo(GlobalMemory memory) {
        SystemInfoQueryVo.Memory sysMemory = new SystemInfoQueryVo.Memory();
        sysMemory.setTotal(memory.getTotal());
        sysMemory.setUsed(memory.getTotal() - memory.getAvailable());
        sysMemory.setFree(memory.getAvailable());
        return sysMemory;
    }

    /**
     * 设置服务器信息
     */
    private SystemInfoQueryVo.SysInfo getSysInfo() {
        SystemInfoQueryVo.SysInfo sysInfo = new SystemInfoQueryVo.SysInfo();
        Properties props = System.getProperties();
        sysInfo.setComputerName(new HostInfo().getName());
        sysInfo.setComputerIp(new HostInfo().getAddress());
        sysInfo.setOsName(props.getProperty("os.name"));
        sysInfo.setOsArch(props.getProperty("os.arch"));
        sysInfo.setUserDir(props.getProperty("user.dir"));
        return sysInfo;
    }

    /**
     * 设置Java虚拟机
     */
    private SystemInfoQueryVo.JVM getJvmInfo() throws UnknownHostException {
        SystemInfoQueryVo.JVM jvm = new SystemInfoQueryVo.JVM();
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
        return jvm;
    }


    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
