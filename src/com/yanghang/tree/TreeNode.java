package com.yanghang.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @version 0.1
 * @author yanghang
 *@date 20150528
 *
 */
public class TreeNode {
	private int parentId;
	private int selfId;
	private String text;
	private List<TreeNode> childList = new ArrayList<TreeNode>();

	/**
	 * 默认无参构造函数
	 */
	public TreeNode() {
	}

	
	/**
	 * 构造函数
	 * 
	 * @param selfId
	 * @param parentId
	 * @param text
	 */
	public TreeNode(int selfId, int parentId, String text) {
		this.selfId = selfId;
		this.parentId = parentId;
		this.text = text;
	}

	/**
	 * 返回该节点是否是叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		if (childList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 插入一个child节点到当前节点中
	 * 
	 * @param treeNode
	 */
	public void addChildNode(TreeNode treeNode) {
		childList.add(treeNode);
	}

	/**
	 * 返回当前节点的晚辈集合 递归调用 先序遍历
	 * 
	 * @return
	 */
	public List<TreeNode> getJuniors() {
		List<TreeNode> juniorList = new ArrayList<TreeNode>();
		List<TreeNode> childList = getChildList();
		if (childList.isEmpty()) {
			return juniorList;
		} else {
			int childNumber = childList.size();
			for (int i = 0; i < childNumber; i++) {
				TreeNode junior = childList.get(i);
				juniorList.add(junior);
				juniorList.addAll(junior.getJuniors());
			}
			return juniorList;
		}
	}

	/**
	 * 返回当前节点的孩子集合
	 * 
	 * @return
	 */
	public List<TreeNode> getChildList() {
		return childList;
	}

	/**
	 * 删除当前节点的某个子孙节点 子孙节点下的子树也一起删除
	 * 
	 * @param childId
	 */
	public void deleteChildNode(int childId) {
		List<TreeNode> childList = getChildList();
		int childNumber = childList.size();
		for (int i = 0; i < childNumber; i++) {
			TreeNode child = childList.get(i);
			if (child.getSelfId() == childId) {
				childList.remove(i);
				return;
			}
			else{
				child.deleteChildNode(childId);
			}
		}
	}

	/**
	 * 动态的插入一个新的节点到当前树中
	 * 
	 * @param treeNode
	 * @return
	 */
	public boolean insertJuniorNode(TreeNode treeNode) {
		int juniorParentId = treeNode.getParentId();
		if (this.selfId == juniorParentId) {
			addChildNode(treeNode);
			return true;
		}
		List<TreeNode> childList = this.getChildList();
		int childNumber = childList.size();
		boolean insertFlag;

		for (int i = 0; i < childNumber; i++) {
			TreeNode childNode = childList.get(i);
			insertFlag = childNode.insertJuniorNode(treeNode);
			if (insertFlag == true)
				return true;
		}
		return false;

	}

	/**
	 * 查找树中某个节点
	 * 
	 * @param id
	 * @return
	 */
	public TreeNode findTreeNodeById(int id) {
		if (this.selfId == id)
			return this;
		if (childList.isEmpty()) {
			return null;
		}
		int childNumber = childList.size();
		for (int i = 0; i < childNumber; i++) {
			TreeNode child = childList.get(i);
			TreeNode resultNode = child.findTreeNodeById(id);
			if (resultNode != null) {
				return resultNode;
			}
		}
		return null;
	}
	
	/**
	 * 构造无序的多叉树，返回根节点
	 * @param map
	 * @return
	 */
	public static TreeNode generateTree(Map<Integer, TreeNode> map,int rootId) {
		TreeNode root = null;
		Set entrySet = map.entrySet();
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			TreeNode node = (TreeNode) ((Map.Entry) it.next()).getValue();
			if (node.getParentId() == rootId) {
				root = node;
			} else {
				//将子节点放到父节点下面
				((TreeNode) map.get(node.getParentId())).addChildNode(node);
			}
		}
		return root;
	}

	/**
	 * 树的先序遍历
	 */
	public void traverse() {
		// 可换成其他遍历节点的方法
		System.out.println(selfId);

		if (childList.isEmpty())
			return;
		for (int i = 0; i < childList.size(); i++) {
			childList.get(i).traverse();
		}
	}

	/**
	 * 将整棵树转化为JSON格式
	 */
	public String toString() {    
		  String result = "{\n"
		   + "id : '" + selfId + "',\n"  
		   + "text : '" + text + "',\n"
		   ;  
		  if (!childList.isEmpty()) {  
			   result += "children : "+ childRenToString();  
			  } else {  
			   result += "leaf : true\n";  
			  }  
		  return result + "}\n";  
		 }	
	
	/**
	 * 将孩子节点数据变为JSON格式字符串
	 */
	private String childRenToString() {
		String result = "[\n";
		for (Iterator<TreeNode> it = childList.iterator(); it.hasNext();) {
			result += ((TreeNode) it.next()).toString();
			result += ",\n";
		}
		result = result.substring(0, result.length() - 2);
		result += "]\n";
		return result;
	}

	public void setChildList(List<TreeNode> childList) {
		this.childList = childList;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSelfId() {
		return selfId;
	}

	public void setSelfId(int selfId) {
		this.selfId = selfId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}