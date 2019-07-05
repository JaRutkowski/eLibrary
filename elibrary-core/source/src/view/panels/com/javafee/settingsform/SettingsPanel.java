package com.javafee.settingsform;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.javafee.tabbedform.clients.ClientDataPanel;
import com.javafee.unicomponent.CustomJTree;

import lombok.Getter;

public class SettingsPanel extends JPanel {

	@Getter
	private JTree treeMenu;

	@Getter
	private JPanel contentPanel;

	public SettingsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{137, 333, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 5, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		treeMenu = new CustomJTree(constructTreeMenuNodes(), true);
		scrollPane.setViewportView(treeMenu);

		contentPanel = new ClientDataPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		add(contentPanel, gbc_panel);
	}

	private List<Object> constructTreeMenuNodes() {
		List<Object> subsubnodes = new ArrayList<>();
		subsubnodes.add("third");
		subsubnodes.add("b11");
		subsubnodes.add("b22");

		List<Object> subnodes = new ArrayList<>();
		subnodes.add("second");
		subnodes.add("b1");
		subnodes.add(subsubnodes);
		subnodes.add("b2");
		subnodes.add("b3");

		List<Object> nodes = new ArrayList<>();
		nodes.add("first");
		nodes.add("a");
		nodes.add("b");
		nodes.add(subnodes);
		nodes.add("c");
		nodes.add("c");

		return nodes;
	}
}
