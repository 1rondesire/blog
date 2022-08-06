package com;

public class test {



    public ListNode addnode(ListNode l1,ListNode l2,int num){
        if (l1==null && l2==null && num==0){
            return null;
        }
        int val = num;
        if (l1!=null){
            val = l1.val+val;
            l1=l1.next;
        }
        if (l2!=null){
            val = l2.val+val;
            l2 = l2.next;
        }
        ListNode node = new ListNode(val % 10);
        node.next=addnode(l1,l2,val/10);
        return node;
    }
}




 class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
