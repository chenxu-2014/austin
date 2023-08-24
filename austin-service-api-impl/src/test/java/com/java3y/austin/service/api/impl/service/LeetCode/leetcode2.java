package com.java3y.austin.service.api.impl.service.LeetCode;

public class leetcode2 {
      public static class ListNode {
          int val;
          ListNode next;
          ListNode() {}
          ListNode(int val) { this.val = val; }
          ListNode(int val, ListNode next) { this.val = val; this.next = next; }
      }

    public static void main(String[] args) {
        ListNode l1 =new ListNode(2,new ListNode(3,new ListNode(5)));
        System.out.println(l1.val);
        System.out.println(l1.next.val);
    }
}
