package com.mydesign.modes.datastructure;

import com.orhanobut.logger.Logger;

/**
 * 单链表的实现;
 * 从头到尾依次相连，元素不唯一；可以允许null;
 */

public class SingleLinkedList<T> {

    //头结点
    private Node<T> header;

    public SingleLinkedList() {
    }

    public void insert(T[] arr) {
        for (T t : arr) {
            insertLast(t);
        }
    }

    public void insertLast(T item) {
        if (isEmpty()) {
            header = new Node<T>(item);
            return;
        }
        Node<T> node = header;
        while (node.next != null) {
            node = node.next;
        }
        Node<T> newNode = new Node<>(item);
        node.setNext(newNode);
    }


    public void insertFirst(T item) {
        if (isEmpty()) {
            header = new Node<>(item);
            return;
        }

        Node<T> newNode = new Node<>(item);
        Node<T> tem = header;
        header = newNode;
        header.setNext(tem);
    }

    public Node<T> remove(T item) {
        if (isEmpty()) {
            return null;
        }
        Node<T> node = header;
        if (node.item == item) {
            return deleteHeaderNode();
        } else {
            //找到要删除节点的上一个节点；
            while (node.next != null) {
                if (item.equals(node.next.item)) {
                    break;
                }
                node = node.next;
            }
            Node<T> delNode = node.next;
            if (delNode != null) {
                node.setNext(delNode.next);
                return delNode;
            }
        }
        return null;
    }

    //删除头结点
    private Node<T> deleteHeaderNode() {
        Node<T> deleteNode = header;
        if (deleteNode.next == null) {
            header = null;
        } else {
            header = deleteNode.next;
        }
        return deleteNode;
    }


    public boolean isEmpty() {
        return header == null;
    }

    /**
     * 反转单链表
     * 反转 1-2-3-4-5-6
     * 6-5-4-3-2-1
     */
    public void reversal() {
        Node node = header;
        reversal(node);
    }

    /**
     * 遍历法
     * 反转链表：将当前节点（curNode）和上一个节点（preNode）记录下来，让curNode的next指向preNode;
     * 重新为header节点赋值；
     *
     * @param node
     */
    public void reversal(Node<T> node) {
        Node<T> preNode = null;
        Node<T> curNode = node;
        while (curNode != null) {
            Node<T> next = curNode.next;
            curNode.setNext(preNode);
            //保存上一个节点和当前节点
            preNode = curNode;
            curNode = next;
        }
        //重新赋值header节点
        header = preNode;
    }


    public void traversal() {
        if (isEmpty()) {
            return;
        }
        Node<T> node = header;
        StringBuilder stringBuilder = new StringBuilder();
        while (node != null) {
            stringBuilder.append(node.item).append("  ");
            node = node.next;
        }

        Logger.e(stringBuilder.toString());
    }

    static class Node<T> {

        private T item;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

    }
}
