package com.javafee.unicomponent;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.javafee.common.Utils;

public class CustomJTree extends JTree {
	public CustomJTree(List<Object> nodes, boolean isSingleSelection) {
		setModel(new DefaultTreeModel(createNodes(nodes)));
		setSelectionModel(isSingleSelection);
		setCustomCellRenderer();

		setBackground(Utils.getApplicationColor());
	}

	private DefaultMutableTreeNode createNodes(List<Object> nodes) {
		AtomicInteger index = new AtomicInteger(0);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(nodes.get(index.get()));
		nodes.forEach(node -> {
			if (index.get() != 0) {
				if (node instanceof String)
					top.add(new DefaultMutableTreeNode(node));
				else {
					top.add(createNodes((List<Object>) node));
				}
			}
			index.incrementAndGet();
		});
		return top;
	}

	private void setSelectionModel(boolean isSingleSelection) {
		if (isSingleSelection)
			getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		else
			getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	}

	private void setCustomCellRenderer() {
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) getCellRenderer();
		Icon icon = new ImageIcon(CustomJTree.class.getResource("/images/btnTreeMenuClose-ico.png"));
		renderer.setClosedIcon(icon);
		renderer.setOpenIcon(icon);
		renderer.setLeafIcon(icon);
	}
}
