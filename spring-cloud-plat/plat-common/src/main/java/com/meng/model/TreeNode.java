package com.meng.model;

import com.meng.util.ReflectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zkzc-mcy on 2017/9/20.
 */
public class TreeNode<T> implements Serializable {
    /**
     * 节点编号
     */
    private Integer id;

    /**
     * 级联id 使用-分隔
     */
    private String cascadeId = "";

    /**
     * 节点名称
     */
    private String name;

    /**
     * 级联名称 使用-分隔
     */
    private String cascadeName = "";

    /**
     * 节点层级
     */
    private Integer deep = 0;

    /**
     * 节点类型（备用）
     */
    private String type;
    /**
     * 节点数据
     */
    private T data;
    /**
     * 节点的子节点
     */
    private List<TreeNode> children;
    /**
     * 节点的父节点
     */
    private TreeNode<T> parent;

    public TreeNode() {
    }
    public TreeNode(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<TreeNode> getChildren() {
        return children;
    }
    public void setChildren(List<TreeNode> children) {
        if (children != null) {
            for (TreeNode node : children) {
                node.setParent(this);
            }
            this.children = children;
        }
    }

    public TreeNode<T> getParent() {
        return parent;
    }
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }
    /**
     * 是否有子节点
     *
     * @return
     */
    public boolean hasChildren() {
        return (children == null || children.size() == 0) ? false : true;
    }
    /**
     * 是否有父节点
     *
     * @return
     */
    public boolean hasParent() {
        return null == parent ? false : true;
    }
    /**
     * 添加子节点
     * @param index 指定位置
     * @param child 子节点
     */
    public void addChild(Integer index, TreeNode<T> child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        child.setParent(this);
        child.setDeep(this.deep + 1);
        Integer id = child.getId();
        String name = child.getName();
        child.setCascadeId(this.cascadeId.equals("") ? String.valueOf(id) : (this.cascadeId + "-" + id));
        child.setCascadeName(this.cascadeName.equals("") ? name : this.cascadeName + "-" + name);
        if(index != null && index >=0){
            children.add(index, child);
        }else{
            children.add(child);
        }
    }
    public void addChild(TreeNode<T> child){
        addChild(null, child);
    }

    /**
     * 添加子节点
     * @param id
     * @param name
     * @param index
     * @param childData
     */
    public void addChild(Integer id, String name, Integer index, T childData) {
        TreeNode<T> child = new TreeNode<>();
        child.setData(childData);
        child.setId(id);
        child.setName(name);
        addChild(index, child);
    }
    public void addChild(Integer id, String name, T childData){
        addChild(id, name, null, childData);
    }

    /**
     * 获取所有叶节点
     *
     * @return
     */
    public List<TreeNode> leaves() {
        List<TreeNode> leaves = new ArrayList();
        if (hasChildren()) {
            for (TreeNode node : children) {
                if (!node.hasChildren()) {
                    leaves.add(node);
                } else {
                    if (node.leaves() != null) {
                        leaves.addAll(node.leaves());
                    }
                }
            }
            return leaves;
        }
        return null;
    }

    /**
     * 节点过滤
     * @param nodeFilter
     */
    public boolean filter(NodeFilter nodeFilter){
        if(hasChildren()){
            Iterator<TreeNode> iterator = children.iterator();
            while (iterator.hasNext()){
                TreeNode node = iterator.next();
                if(node.hasChildren()){
                    boolean keep = node.filter(nodeFilter);
                    if(!keep){
                        if(!nodeFilter.filter(node)){
                            iterator.remove();
                        }
                    }
                }else{
                    if(!nodeFilter.filter(node)){
                        iterator.remove();
                    }
                }
            }

            // 判断子节点是否全部被移除
            if(hasChildren()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public String getCascadeId() {
        return cascadeId;
    }

    public void setCascadeId(String cascadeId) {
        this.cascadeId = cascadeId;
    }

    public String getCascadeName() {
        return cascadeName;
    }

    public void setCascadeName(String cascadeName) {
        this.cascadeName = cascadeName;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    /**
     * 将列表转换为树结构
     * @param list 列表，默认节点id字段名为id；父节点id字段名称为pid；节点名称字段为name
     * @return
     * @throws IllegalAccessException
     */
    public static TreeNode createTreeByList(List list) throws IllegalAccessException {
        return createTreeByList(list, "id", "pid", "name");
    }
    public static TreeNode createTreeByList(List list,String idFiled,String pidFiled,String nameFiled)throws IllegalAccessException{
        return createTreeByList(0,"root", list, idFiled, pidFiled, nameFiled);
    }
    public static TreeNode createTreeByList(Integer rootId,String rootName,List list, String idFiled, String pidFiled, String nameFiled) throws IllegalAccessException {
        TreeNode<Object> root = new TreeNode<>(rootId, rootName);
        if (list != null && list.size() > 0) {
            Iterator<Object> iterator = list.iterator();
            // 一级子节点
            while (iterator.hasNext()) {
                Object item = iterator.next();
                Integer pid = Integer.parseInt(ReflectUtils.getFieldValue(item, pidFiled).toString());
                if (pid.equals(rootId)) {
                    Integer id = Integer.parseInt(ReflectUtils.getFieldValue(item, idFiled).toString());
                    root.addChild(id, ReflectUtils.getFieldValue(item, nameFiled).toString(), item);
                    iterator.remove();
                }
            }

            // 高层节点，默认总共支持5层，防止游离节点问题陷入死循环
            int maxDeep = 4;
            int i = 0;
            while (list.size() > 0 && i < maxDeep) {
                List<TreeNode> leaves = root.leaves();
                if(leaves != null){
                    for (TreeNode leaf : leaves) {
                        iterator = list.iterator();
                        while (iterator.hasNext()) {
                            Object item = iterator.next();
                            int pid = Integer.parseInt(ReflectUtils.getFieldValue(item, pidFiled).toString());
                            if (pid == leaf.getId()) {
                                int id = Integer.parseInt(ReflectUtils.getFieldValue(item, idFiled).toString());
                                leaf.addChild(id, ReflectUtils.getFieldValue(item, nameFiled).toString(), item);
                                iterator.remove();
                            }
                        }
                    }
                }
                i++;
            }
        }
        return root;
    }

    /**
     * 节点过滤接口
     */
    public interface NodeFilter{
        boolean filter(TreeNode node);
    }
}
