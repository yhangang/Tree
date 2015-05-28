package com.yanghang.run;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yanghang.tree.TreeNode;

public class Run {

	public static void main(String[] args) {
		TreeNode root = null;

		// 加载虚拟数据
		List<HashMap<String, String>> dataList = VirtualDataGenerator.getVirtualResult();
		// 节点列表（散列表，用于临时存储节点对象）
		HashMap<Integer, TreeNode> nodeMap = new HashMap<>();
		// 根据结果集构造节点列表（存入散列表）
		for (Iterator<HashMap<String, String>> it = dataList.iterator(); it.hasNext();) {
			Map<String,String> dataRecord = (Map<String,String>) it.next();
			TreeNode node = new TreeNode();
			node.setSelfId(Integer.valueOf((String) dataRecord.get("id")));
			node.setParentId(Integer.valueOf((String) dataRecord
					.get("parentId")));
			node.setText((String) dataRecord.get("text"));
			nodeMap.put(node.getSelfId(), node);
		}

		root = TreeNode.generateTree(nodeMap, 0);

		// root.deleteChildNode(112000);
		TreeNode node = new TreeNode();
		node.setSelfId(1234);
		node.setParentId(113000);
		node.setText("啦啦");
		root.insertJuniorNode(node);

		// 输出无序的树形菜单的JSON字符串
		 System.out.println(root.toString());
//		List<TreeNode> juniorList = root.getJuniors();
//		
//		for(TreeNode t:juniorList){
//			System.out.println(t.getSelfId());
//		}
		
	}

}
