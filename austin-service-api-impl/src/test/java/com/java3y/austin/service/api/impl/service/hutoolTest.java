package com.java3y.austin.service.api.impl.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class hutoolTest {
    public static void main(String[] args) {
        String a = "123456789";
        String[] b = {"123456789","wwww"};
        String sbc = Convert.toSBC(a);
        String aStr = Convert.toStr(a);
        System.out.println(sbc);
        List<?> list = Convert.toList(b);
        System.out.println(list.get(1));
        String strDate = "2017-05-06";
        Date value = Convert.toDate(strDate);
        System.out.println(value);
//当前时间
        Date date = DateUtil.date();
//当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
//当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
//当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
//当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
        System.out.println(date);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(now);
        System.out.println(today);
        System.out.println(Calendar.getInstance()+"===="+System.currentTimeMillis());


    }
}
