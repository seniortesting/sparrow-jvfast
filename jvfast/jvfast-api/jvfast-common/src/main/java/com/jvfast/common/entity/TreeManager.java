package com.jvfast.common.entity;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 递归树遍历
 */
public class TreeManager<T extends Tree> {

    private List<T> treeList;
    private Long rootPid;
    private List<Long> pids = new ArrayList<>();


    public TreeManager(List<T> treeList, Long rootPid) {
        this.treeList = treeList;
        this.rootPid = rootPid;
    }

    /**
     * 将节点数组归并为一个森林（多棵树）（填充节点的children域）
     * 时间复杂度为O(n^2)
     *
     * @param items 节点域
     * @param <T>   T 泛型标记
     * @return 多棵树的根节点集合
     */
    public static <T extends Tree> List<T> merge(List<T> items, Long rootPid) {
        TreeManager<T> treeManager = new TreeManager<>(items, rootPid);
        items.forEach(tree -> {
            Long id = tree.getId();
            Long pid = tree.getPid();
            if (!pid.equals(rootPid)) {
                Tree node = treeManager.getTreeNodeById(pid);
                if (node != null) {
                    node.getChildren().add(tree);
                } else {
                    treeManager.addPId(id);
                }
            }
        });
        return treeManager.getRoot();
    }

    /**
     * 查询树节点的所有子节点id集合
     *
     * @param items
     * @param id
     * @return java.util.List<java.lang.Long>
     * @author Walter Hu
     * @date 2019/12/17
     * @since 1.8
     */
    public static <T extends Tree> List<Long> mergeId(List<T> items, Long id) {
        List<Long> ids = Lists.newArrayList();
        TreeManager<T> treeManager = new TreeManager<>(items, id);
        Tree tree = treeManager.getTreeNodeById(id);
        if (Objects.nonNull(tree)) {
            ids.add(id);
            ids.addAll(treeManager.findTreeChildren(tree));
        }else{
            ids.add(id);
        }
        return ids;
    }

    /**
     * 查询树节点下的所有子节点id集合
     *
     * @param tree
     * @return java.util.List<java.lang.Long>
     * @author Walter Hu
     * @date 2019/12/17
     * @since 1.8
     */
    private List<Long> findTreeChildren(Tree tree) {
        List<Long> currentIds = Lists.newArrayList();
        List<Tree> childTree = getTreeNodeByPId(tree.getId());
        if (!childTree.isEmpty()) {
            childTree.forEach(t -> {
                currentIds.add(t.getId());
                currentIds.addAll(findTreeChildren(t));
            });
        }
        return currentIds;
    }

    /**
     * 根据节点ID获取一个节点
     *
     * @param id 节点ID
     * @return 对应的节点对象
     */
    private Tree getTreeNodeById(Long id) {
        for (Tree tree : treeList) {
            if (id.equals(tree.getId())) {
                return tree;
            }
        }
        return null;
    }

    private List<Tree> getTreeNodeByPId(Long pid) {
        List<Tree> list = Lists.newArrayList();
        for (Tree tree : treeList) {
            if (pid.equals(tree.getPid())) {
                list.add(tree);
            }
        }
        return list;
    }

    /**
     * 增加父节点ID
     *
     * @param pid 父节点ID
     */
    private void addPId(Long pid) {
        pids.add(pid);
    }

    /**
     * 获取树的根节点(一个森林对应多颗树)
     *
     * @return 树的根节点集合
     */
    private List<T> getRoot() {
        List<T> roots = new ArrayList<>();
        for (T tree : treeList) {
            if (tree.getPid().equals(rootPid) || pids.contains(tree.getId())) {
                roots.add(tree);
            }
        }
        return roots;
    }

}
