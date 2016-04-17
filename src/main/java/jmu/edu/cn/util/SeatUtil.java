package jmu.edu.cn.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Administrator on 2016/4/16.
 */
public class SeatUtil {
    public static List<String> seatSets = Lists.newArrayList();

    static {
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 16; j++) {
                seatSets.add(i + "车箱座位" + j);
            }
        }
    }
}
