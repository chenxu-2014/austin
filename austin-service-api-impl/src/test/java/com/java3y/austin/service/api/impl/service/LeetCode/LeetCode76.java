package com.java3y.austin.service.api.impl.service.LeetCode;

import java.util.HashMap;

public class LeetCode76 {
    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC","AA"));
    }
    public static String minWindow(String s, String t) {
        int sLen = s.length();
        HashMap<Character, Integer> window = new HashMap<Character, Integer>();
        HashMap<Character, Integer> need = new HashMap<Character, Integer>();
        //将字符串 t 中的字符存入哈希表 need 中，key/value 为字符/对应出现的次数
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;
        //valid 记录窗口中 t 中不重复字符的个数
        int valid = 0;
        //记录最小覆盖子串的起始索引以及长度
        int start = 0, minLen = Integer.MAX_VALUE;
        while (right < sLen) {
            char c = s.charAt(right);
            right++;
            //更新窗口内的数据，如果字符 c 存在于 t 中，则将其加入到 window 中
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                /*
                    如果 t 中所有的字符 c 都已经加入到窗口中
                    注意：使用"=="比较 Integer 类型的值时，值的范围只能在 -128 ~ 127 之间，否则会出错
                    详情请见https://www.cnblogs.com/mrhgw/p/10449391.html
                */
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            //判断左侧窗口是否要收缩，t 中的所有字符都已经加入到窗口中
            while (valid == need.size()) {
                //更新最小覆盖子串
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }
                //d 是即将移出窗口的字符
                char d = s.charAt(left);
                left++;
                //更新窗口内的数据
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return (minLen == Integer.MAX_VALUE) ? "" : s.substring(start, start + minLen);
    }
}
