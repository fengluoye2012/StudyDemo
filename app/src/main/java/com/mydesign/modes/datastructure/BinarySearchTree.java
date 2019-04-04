package com.mydesign.modes.datastructure;


import android.util.Log;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 树集合了数组（查找速度快）和链表（插入、删除速度快）的优点
 * 查找二叉树:在插入时 需要将插入的元素按照一定的顺序排列好，否则可能会成为链表结构，就失去了二叉查找树的优势了；
 * <p>
 * 二叉搜索树的效率：
 * 树的大部分操作需要从上至下一层层的查找树的节点，对于一棵满树，大约有一半的节点处于最底层（最底层节点数 = 其它层节点数的和 + 1），
 * 故节点操作大约有一半需要找到最底层节点，大约有四分之一的节点处于倒数第二层，故节点操作大约有四分之一需要找到倒数第二层节点，依此类推
 * 查找过程中，需要访问每一层的节点，故只要知道了查找的层数，就能知道操作所需的时间，如果节点总数为N，层数为L，L=log2(N+1)
 * 如果为查找操作或删除操作，被操作的节点可能是是树的任意节点，故查找操作或删除操作的时间复杂度为：1/21*log2(N+1) + 1/22*log2(N/2+1) + ... + 1/2N*1
 * 如果为插入操作，由于每次都在树的最低层插入新的节点，故插入操作的时间复杂度为：log2(N+1)
 * 总的来说可以认为二叉搜索树操作的时间复杂度为为O(logN)
 * 如果树不是一棵满树，则判断起来比较复杂，但是如果层数相同，对满树的操作肯定比对不满树的操作更耗时
 * <p>
 * 对于一个含有10000个节点的二叉搜索树，查找操作大约需要13次
 * 二叉搜索树集合了有序链表插入删除效率高和有序数组查询效率高的优点
 */
public class BinarySearchTree {

    private String TAG = BinarySearchTree.class.getSimpleName();
    protected TreeNode root;

    public BinarySearchTree() {

    }

    public void createTree() {
        TreeNode treeNode = new TreeNode(15, null);

        root = treeNode;

        TreeNode treeNode1 = new TreeNode(10, treeNode);
        TreeNode treeNode4 = new TreeNode(25, treeNode);
        treeNode.setLiftChild(treeNode1);
        treeNode.setRightChild(treeNode4);

        TreeNode treeNode2 = new TreeNode(7, treeNode1);
        TreeNode treeNode3 = new TreeNode(13, treeNode1);
        treeNode1.setLiftChild(treeNode2);
        treeNode1.setRightChild(treeNode3);

        TreeNode treeNode5 = new TreeNode(18, treeNode4);
        TreeNode treeNode6 = new TreeNode(30, treeNode4);

        treeNode4.setLiftChild(treeNode5);
        treeNode4.setRightChild(treeNode6);
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
            //insertNode(i);
            insertNodeRec(i);
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public boolean isEmptyTree() {
        return root == null;
    }

    /**
     * 添加一个节点；如果该树中不存在该节点，直接添加；已存在，直接返回对应值的节点；
     *
     * @param item
     * @return
     */
    TreeNode insertNode(int item) {
        if (isEmptyTree()) {
            TreeNode node = new TreeNode(item, null);
            root = node;
            return node;
        }
        TreeNode node = root;
        TreeNode parentNode = node;

        //node == null 就是要插入的位置，parentNode就是node的双亲节点
        while (node != null) {
            parentNode = node;
            if (node.item > item) {
                node = node.getLiftChild();
            } else if (node.item < item) {
                node = node.getRightChild();
            } else {
                return node;
            }
        }

        node = new TreeNode(item, parentNode);
        //判断是左节点还是右节点；
        if (parentNode.item > item) {
            parentNode.setLiftChild(node);
        } else if (parentNode.item < item) {
            parentNode.setRightChild(node);
        }
        return node;
    }


    void insertNodeRec(int item) {
        if (isEmptyTree()) {
            root = new TreeNode(item, null);
            return;
        }
        insertNodeRec(item, root);
    }

    /**
     * 递归的方法插入
     *
     * @param item
     */
    void insertNodeRec(int item, TreeNode parentNode) {
        TreeNode treeNode = parentNode;
        if (treeNode.getItem() > item) {
            if (treeNode.getLiftChild() != null) {
                treeNode = treeNode.getLiftChild();
                insertNodeRec(item, treeNode);
            } else {
                TreeNode node = new TreeNode(item, treeNode);
                treeNode.setLiftChild(node);
            }

        } else if (treeNode.getItem() < item) {
            if (treeNode.getRightChild() != null) {
                treeNode = treeNode.getRightChild();
                insertNodeRec(item, treeNode);
            } else {
                TreeNode node = new TreeNode(item, treeNode);
                treeNode.setRightChild(node);
            }
        } else {
            //存在该节点
        }
    }

    /**
     * 深度优先搜索算法（Depth First Search），是搜索算法的一种。是沿着树的深度遍历树的节点，尽可能深的搜索树的分支。
     * 递归的方式，不断往下找
     * 分为前序遍历，中序遍历，后序遍历
     * <p>
     * *******************15
     * <p>
     * **********10               25
     * <p>
     * ******7      13        18       30
     * <p>
     * *** 5 ******** 14
     * <p>
     * <p>
     * 前序遍历（先中 再左 后右）  15  10 7  5  13  14  25 18 30
     */
    public void preOderSearch(TreeNode node) {
        if (node == null) {
            return;
        }
        Log.e(TAG, node.getItem() + "");
        preOderSearch(node.getLiftChild());
        preOderSearch(node.getRightChild());
    }

    /**
     * 中序遍历（先左 再中  后右） 5 7 10 13 14 15 18 25 30
     */
    public void midOrderSearch(TreeNode node) {
        if (node == null) {
            return;
        }
        midOrderSearch(node.getLiftChild());
        Log.e(TAG, node.getItem() + "");
        midOrderSearch(node.getRightChild());
    }

    /**
     * 后序遍历（先左 再右  后中） 5 7 13 14 10 18 30 25 15
     */
    public void postOrderSearch(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderSearch(node.getLiftChild());
        postOrderSearch(node.getRightChild());
        Log.e(TAG, node.getItem() + "");
    }

    /**
     * 非递归前序遍历：先中 在左 再右
     * 采用栈的思想：先进后出
     *
     * @param node
     */
    public void preNotRecSearch(TreeNode node) {
        TreeNode treeNode = node;
        Stack<TreeNode> stack = new Stack<>();

        while (treeNode != null || !stack.isEmpty()) {
            //遍历 找到该节点的左子树的左节点，并且加入栈中
            while (treeNode != null) {
                Log.e(TAG, treeNode.getItem() + "");
                stack.push(treeNode);
                treeNode = treeNode.getLiftChild();
            }

            //取出栈顶的节点（最新加入的）
            if (!stack.isEmpty()) {
                //此时treeNode可能为null,但是stack依然有元素，要在最外层while加上!stack.isEmpty()；
                treeNode = stack.pop().getRightChild();
            }
        }
    }

    /**
     * 非递归 中序排序（左 中 右）
     * 采用stack 先进后出 思想
     *
     * @param node
     */
    public void midNotRecSearch(TreeNode node) {
        TreeNode treeNode = node;
        Stack<TreeNode> stack = new Stack<>();
        while (treeNode != null || !stack.isEmpty()) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.getLiftChild();
            }

            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                Log.e(TAG, treeNode.getItem() + "");
                //得到的元素可能为null,所以在最外层while循环，加入!stack.isEmpty()判断；
                treeNode = treeNode.getRightChild();
            }
        }
    }

    /**
     * 广度搜索 是从根节点开始，沿着树的宽度遍历树的节点。如果所有节点均被访问，则算法中止。
     * 借助队列数据结构，由于队列是先进先出的顺序，因此可以先将左子树入队，然后再将右子树入队。
     * *******************15
     * <p>
     * **********10               25
     * <p>
     * ******7      13        18       30
     * <p>
     * *** 5 ******** 14
     *
     * @param node
     */
    public void breadthFirstSearch(TreeNode node) {
        if (node == null) {
            return;
        }

        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();
        //现将开始节点 入队
        queue.offer(node);

        while (!queue.isEmpty()) {
            //出队
            TreeNode poll = queue.poll();
            Log.e(TAG, poll.getItem() + "");

            //把出队节点的左右子节点加入到队列中
            if (poll.getLiftChild() != null) {
                queue.offer(poll.getLiftChild());
            }
            if (poll.getRightChild() != null) {
                queue.offer(poll.getRightChild());
            }
        }
    }

    /**
     * 删除对应的节点
     * 1）子节点；直接删除；
     * 2）只有左子节点；删除对应的节点，左子节点顶上；
     * 3）只有右子节点；删除对应的节点，右子节点顶上；
     * 4）左右子节点都有；删除该节点，其后序节点顶上；
     *
     * @param item
     */
    public void remove(int item) {
        TreeNode node = root;
        //空树
        if (node == null) {
            return;
        }
        TreeNode treeNode = getTreeNode(node, item);
        deleteTreeNode(treeNode);
    }

    private void deleteTreeNode(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        //删除的节点是叶子节点
        if (isLeafNode(treeNode)) {
            deleteLeafNode(treeNode);
        } else if (onlyLeftChild(treeNode)) {
            deleteHasOnlyChildNode(treeNode, true);
        } else if (onlyRightChild(treeNode)) {
            deleteHasOnlyChildNode(treeNode, false);
        } else {
            deleteHasChildrenNode(treeNode);
        }
    }

    /**
     * 删除 同时有左子节点，右子节点的节点
     * 1：要找到该节点的后序节点：即找到该节点的右子树的最左节点
     * 2：对最左节点根据前三种情况判断
     *
     * @param treeNode
     */
    private void deleteHasChildrenNode(TreeNode treeNode) {
        TreeNode leftNode = getLeftNode(treeNode.getRightChild());
        deleteTreeNode(leftNode);
        treeNode.item = leftNode.item;
    }

    /**
     * 找到该节点的右子树的最左节点
     *
     * @param treeNode
     */
    private TreeNode getLeftNode(TreeNode treeNode) {
        while (treeNode.getLiftChild() != null) {
            treeNode = treeNode.getLiftChild();
        }
        return treeNode;
    }

    /**
     * 删除只有一个子节点的节点
     *
     * @param treeNode
     * @param left
     */
    private void deleteHasOnlyChildNode(TreeNode treeNode, boolean left) {
        TreeNode parent = treeNode.getParent();
        TreeNode childNode;
        if (left) {
            childNode = treeNode.getLiftChild();
            parent.setLiftChild(childNode);
        } else {
            childNode = treeNode.getRightChild();
            parent.setRightChild(childNode);
        }
        childNode.setParent(parent);
        treeNode.setParent(null);
    }

    /**
     * 删除叶子结点
     *
     * @param treeNode
     */
    private void deleteLeafNode(TreeNode treeNode) {
        TreeNode parent = treeNode.getParent();
        if (treeNode.item < parent.item) {
            parent.setLiftChild(null);
        } else {
            parent.setRightChild(null);
        }
        treeNode.setParent(null);
    }


    private boolean onlyLeftChild(TreeNode treeNode) {
        return (treeNode.getLiftChild() != null && treeNode.getRightChild() == null);
    }

    private boolean onlyRightChild(TreeNode treeNode) {
        return (treeNode.getLiftChild() == null && treeNode.getRightChild() != null);
    }


    /**
     * 当前节点是叶子节点
     *
     * @param treeNode
     * @return
     */
    private boolean isLeafNode(TreeNode treeNode) {
        return (treeNode.getRightChild() == null && treeNode.getLiftChild() == null);
    }

    /**
     * 找到对应的节点；如果存在则返回对应的节点，不存在则返回null;
     *
     * @param node root节点
     * @param item 目标值
     * @return
     */
    private TreeNode getTreeNode(TreeNode node, int item) {

        if (node == null) {
            return null;
        }
        if (node.item > item) {
            node = getTreeNode(node.getLiftChild(), item);
        } else if (node.item < item) {
            node = getTreeNode(node.getRightChild(), item);
        }
        return node;
    }


    class TreeNode {
        int item;
        TreeNode liftChild;
        TreeNode rightChild;
        TreeNode parent;

        public TreeNode(int item, TreeNode parentNode) {
            this.item = item;
            this.parent = parentNode;
        }

        public int getItem() {
            return item;
        }

        public TreeNode getLiftChild() {
            return liftChild;
        }

        public void setLiftChild(TreeNode liftChild) {
            this.liftChild = liftChild;
        }

        public TreeNode getRightChild() {
            return rightChild;
        }

        public void setRightChild(TreeNode rightChild) {
            this.rightChild = rightChild;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }
    }
}
