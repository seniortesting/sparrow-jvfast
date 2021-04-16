package com.jvfast.common.util;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 搜索附近商品算法
 */
public class LatLngUtil {
    /**
     * 地球的平均半径： 6371。0km， 赤道半径 6378.137km
     */
    private static double EARTH_RADIUS = 6371.0;

    /**
     * 默认的搜索范围，100公里
     */
    public static final double DEFAULT_RADIUS = 200;

    @Data
    @AllArgsConstructor
    public static class Tuple {

        private double minlat;
        private double maxlat;
        private double minlng;
        private double maxlng;
    }

    /**
     * 推荐方法
     * 方法一:
     * 计算当前位置方圆多少千米的四个点坐标
     * 然后将取得的四个点带入数据库查询的时候对应的数据的
     * where lat between minlat and maxlat AND lng between minlng and maxlng
     * <p>
     * 先算出该点周围的矩形的四个点，然后使用经纬度去直接匹配数据库中的记录
     * 参考算法： https://cn.charlee.li/location-search.html
     * 算法优化： https://blog.csdn.net/u011001084/article/details/52980834
     * <p>
     * 参考类库： https://blog.csdn.net/hylexus/article/details/78734745
     * 有一个相同的类库： spatial4j
     *
     * @param lat
     * @param lng
     * @param radius 千米
     */
    public static Tuple findNeighPosition(double lat, double lng, double radius) {
        //⊿λ东西两侧的的范围边界
        double radians = Math.toRadians(lat);
        double dlat = radius / EARTH_RADIUS;
        double dlng = 2 * Math.asin(Math.sin(radius / (2 * EARTH_RADIUS)) / Math.cos(radians));
        //弧度转为角度
        dlng = Math.toDegrees(dlng);
        //⊿φ南北两侧的范围边界
        dlat = Math.toDegrees(dlat);

        // 得出四个点的坐标
//        left-top    : (lat + dlat, lng - dlng)
//        right-top   : (lat + dlat, lng + dlng)
//        left-bottom : (lat - dlat, lng - dlng)
//        right-bottom: (lat - dlat, lng + dlng)
        double minlat = lat - dlat;
        double maxlat = lat + dlat;
        double minlng = lng - dlng;
        double maxlng = lng + dlng;

        return new Tuple(minlat, maxlat, minlng, maxlng);
    }

    /**
     * 方法二:
     * 先查询所有的当前区域的所有记录:
     * 然后遍历所有的每条记录通过该方法计算距离,并排序输出
     * <p>
     * 计算两点经纬度的距离，返回公里/千米： https://blog.csdn.net/u011001084/article/details/52980834
     * 不错的文章介绍： mysql get_distance函数： https://www.jianshu.com/p/94f99d70c71d
     * st_distance 计算的结果单位是度，需要乘111195（地球半径6371000*PI/180）
     * <p>
     * 100w 144毫秒计算方法
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        //用haversine公式计算球面两点间的距离。
        //经纬度转换成弧度
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);

        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);

        //差值
        double vLat = Math.abs(lat1 - lat2);
        double vLon = Math.abs(lng1 - lng2);

        //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double h = HaverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * HaverSin(vLon);
        // 转换的千米，公里距离： 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h))
        double distance = 12742 * Math.asin(Math.sqrt(h));
        // 转换成米 90.07909886567282
        distance = Math.round(distance * 10000d);
        return distance;
    }


    /**
     * 方法三:
     * 先查询所有的当前区域的所有记录:
     * 然后遍历所有的每条记录通过该方法计算距离,并排序输出(优化版)
     * <p>
     * 经纬度计算距离
     * 100w 11毫秒计算法
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double distanceSimplify(double lat1, double lng1, double lat2, double lng2) {
        // 经度差值
        double dx = lng1 - lng2;
        // 纬度差值
        double dy = lat1 - lat2;
        // 平均纬度
        double b = (lat1 + lat2) / 2.0;
        // 东西距离
        double Lx = Math.toRadians(dx) * 6367000.0 * Math.cos(Math.toRadians(b));
        // 南北距离
        double Ly = 6367000.0 * Math.toRadians(dy);
        // 用平面的矩形对角距离公式计算总距离
        return Math.sqrt(Lx * Lx + Ly * Ly);
    }

    /**
     * 方法四:
     * 先查询所有的当前区域的所有记录:
     * 然后遍历所有的每条记录通过该方法计算距离,并排序输出(美团优化版)
     * <p>
     * 计算两个经纬度的距离
     * 美团优化： https://tech.meituan.com/2014/09/05/lucene-distance.html
     * 经纬度转xyz坐标： https://blog.csdn.net/ruangong1203/article/details/75994424
     * 简化算法在：https://blog.csdn.net/u011001084/article/details/52980834
     * <p>
     * 100w 4毫秒计算方法
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @param a
     * @return
     */
    public static double getDistanceSimplifyMore(double lat1, double lng1, double lat2, double lng2, double[] a) {
        //1) 计算三个参数
        // 经度差值
        double dx = lng1 - lng2;
        // 纬度差值
        double dy = lat1 - lat2;
        // 平均纬度
        double b = (lat1 + lat2) / 2.0;
        //2) 计算东西方向距离和南北方向距离(单位：米)，东西距离采用三阶多项式
        // 东西距离
        double Lx = (a[3] * b * b * b + a[2] * b * b + a[1] * b + a[0]) * Math.toRadians(dx) * 6367000.0;
        // 南北距离
        double Ly = 6367000.0 * Math.toRadians(dy);
        //3) 用平面的矩形对角距离公式计算总距离
        return Math.sqrt(Lx * Lx + Ly * Ly);
    }

    /**
     * haversine公式
     *
     * @param theta
     * @return
     */
    private static double HaverSin(double theta) {
        double sinValue = Math.sin(theta / 2);
        return sinValue * sinValue;
    }

    /**
     * 将角度换算为弧度。
     * Math.toRadians
     *
     * @param degrees
     * @return
     */
    public static double ConvertDegreesToRadians(double degrees) {
        // 0.0174532925199433
        // https://zaixianjisuanqi.51240.com/
        return degrees * Math.PI / 180.0;
    }

    /**
     * 将弧度转为角度
     * Math.toDegrees()
     *
     * @param radian
     * @return
     */
    public static double ConvertRadiansToDegrees(double radian) {
        return radian * 180.0 / Math.PI;
    }
}
