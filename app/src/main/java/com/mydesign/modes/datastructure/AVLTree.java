package com.mydesign.modes.datastructure;

import java.util.List;

/**
 * AVL树本质上还是一棵二叉搜索树，它的特点是：
 * 1.本身首先是一棵二叉搜索树。
 * 2.带有平衡条件：每个结点的左右子树的高度之差的绝对值（平衡因子）最多为1。
 * 也就是说，AVL树，本质上是带了平衡功能的二叉查找树（二叉排序树，二叉搜索树）。
 */
public class AVLTree {

    private String TAG = BinarySearchTree.class.getSimpleName();
    protected AVLTree.AVLTreeNode root;

    public AVLTree() {
    }

    /**
     * 创建查找二叉树
     *
     * @param list
     */
    public void createTree(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i : list) {
            insertNode(i);
        }
    }

    /**
     * 是先添加节点，再找到对应的具体位置；
     *
     * @param item
     * @return
     */
    void insertNode(int item) {
        if (isEmptyTree()) {
            AVLTreeNode node = new AVLTreeNode(item, null);
            node.height = 1;
            root = node;
            return;
        }
        AVLTreeNode parentNode = root;
        AVLTreeNode node = root;

        while (node != null) {
            parentNode = node;
            if (node.getItem() < item) {
                node = node.getLiftChild();
            } else if (node.getItem() < item) {
                node = node.getRightChild();
            } else {
                return;
            }
        }

        node = new AVLTreeNode(item, parentNode);
        if (parentNode.getItem() > item) {
            parentNode.setLiftChild(node);
        } else {
            parentNode.setRightChild(node);
        }
        node.setHeight(1);
        //从插入的节点开始往上，找到第一个不平衡的结点
        searchNotBalanceNode(node, item);
    }

    private void searchNotBalanceNode(AVLTreeNode node, int item) {
        while (node.getParent() != null) {
            AVLTreeNode nodeParent = node.getParent();
            int leftHeight = 0;
            int rightHeight = 0;
            if (nodeParent.getLiftChild() != null) {
                leftHeight = nodeParent.getLiftChild().getHeight();
            }

            if (nodeParent.getRightChild() != null) {
                rightHeight = nodeParent.getRightChild().getHeight();
            }
            nodeParent.setHeight(leftHeight >= rightHeight ? leftHeight + 1 : rightHeight + 1);
            //平衡因子,找到不平衡结点，
            int balance = leftHeight - rightHeight;
            if (balance > 1) {//左左旋转，右左旋转
                if (node.getItem() > item) {//左左旋转
                    nodeParent = llRotate(nodeParent);
                } else {
                    rlRotate(nodeParent);
                }
            } else if (balance < -1) {//右右旋转，左右旋转
                if (node.getItem() < item) {//右右旋转
                    rrRotate(nodeParent);
                } else {
                    lrRotate(nodeParent);
                }
            }
            //
            node = nodeParent;
        }
    }

    private void lrRotate(AVLTreeNode nodeParent) {

    }

    private void rlRotate(AVLTreeNode nodeParent) {

    }

    private void rrRotate(AVLTreeNode nodeParent) {

    }

    /**
     * @param rootNode
     */
    private AVLTreeNode llRotate(AVLTreeNode rootNode) {
        //1：保存根节点的左子树
        AVLTreeNode tem = rootNode.getLiftChild();
        //2：根节点的左子树设置为 tem的右子树
        rootNode.setLiftChild(tem.getRightChild());
        //3：tem的右子树 设置为 root
        tem.setRightChild(rootNode);
        //更新root;
        rootNode = tem;
        return rootNode;
    }

    public boolean isEmptyTree() {
        return root == null;
    }


    //该节点的高度：从该节点出发，最大的层级；
    static class AVLTreeNode {
        int item;
        int height;
        AVLTreeNode liftChild;
        AVLTreeNode rightChild;
        AVLTreeNode parent;

        public AVLTreeNode(int item, AVLTreeNode parentNode) {
            this.item = item;
            this.parent = parentNode;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getItem() {
            return item;
        }

        public AVLTreeNode getLiftChild() {
            return liftChild;
        }

        public void setLiftChild(AVLTreeNode liftChild) {
            this.liftChild = liftChild;
        }

        public AVLTreeNode getRightChild() {
            return rightChild;
        }

        public void setRightChild(AVLTreeNode rightChild) {
            this.rightChild = rightChild;
        }

        public AVLTreeNode getParent() {
            return parent;
        }

        public void setParent(AVLTreeNode parent) {
            this.parent = parent;
        }
    }
}
