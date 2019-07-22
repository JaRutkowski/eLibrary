package com.javafee.uniform;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.javafee.common.BasePanel;
import com.javafee.common.SystemProperties;
import com.javafee.common.Utils;
import com.javafee.unicomponent.border.CustomTitledBorder;
import com.javafee.unicomponent.jcheckbox.CustomJCheckBox;

import lombok.Getter;

public class AdmIsAccountantPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	@Getter
	private JCheckBox chckbxIsAccountant;
	@Getter
	private DecisionPanel decisionPanel;

	public AdmIsAccountantPanel() {
		super();
		setBorder(new CustomTitledBorder(UIManager.getBorder("TitledBorder.border"),
				SystemProperties.getInstance().getResourceBundle()
						.getString("admIsAccountantPanel.admIsAccountantPanelBorderTitle"),
				TitledBorder.LEADING, CustomTitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 161, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		chckbxIsAccountant = new CustomJCheckBox(SystemProperties.getInstance().getResourceBundle()
				.getString("admIsAccountantPanel.chckbxIsAccountant"));
		chckbxIsAccountant.setBackground(Utils.getApplicationUserDefinedColor());
		GridBagConstraints gbc_chckbxIsAccountant = new GridBagConstraints();
		gbc_chckbxIsAccountant.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxIsAccountant.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxIsAccountant.gridx = 0;
		gbc_chckbxIsAccountant.gridy = 0;
		add(chckbxIsAccountant, gbc_chckbxIsAccountant);

		decisionPanel = new DecisionPanel();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) decisionPanel.getLayout();
		gridBagLayout_1.columnWeights = new double[]{0.0, 0.0};
		GridBagConstraints gbc_decisionPanel = new GridBagConstraints();
		gbc_decisionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_decisionPanel.gridx = 1;
		gbc_decisionPanel.gridy = 0;
		add(decisionPanel, gbc_decisionPanel);
	}
}
