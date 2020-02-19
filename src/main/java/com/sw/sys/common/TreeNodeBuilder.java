package com.sw.sys.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 导航页 子节点构造
 * @author: 单威
 * @time: 2020/2/11 14:45
 */
public class TreeNodeBuilder {

    /**
     * @param treeNodes
     * @param topPid
     * @return nodes
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes, Integer topPid) {
        List<TreeNode> nodes = new ArrayList<>();
        for (TreeNode treeNode1 : treeNodes) {
            if (treeNode1.getPid() == topPid) {
                nodes.add(treeNode1);
            }
            for (TreeNode treeNode2 : treeNodes) {
                if (treeNode1.getId() == treeNode2.getPid()) {
                    treeNode1.getChildren().add(treeNode2);
                }
            }
        }
        return nodes;
    }
}
