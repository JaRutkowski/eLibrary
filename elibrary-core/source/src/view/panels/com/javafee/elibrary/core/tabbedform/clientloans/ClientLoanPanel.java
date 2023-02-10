package com.javafee.elibrary.core.tabbedform.clientloans;

import com.javafee.elibrary.core.common.BasePanel;
import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.model.ClientLoanTableModel;
import com.javafee.elibrary.core.model.HistoryLoanTableModel;
import com.javafee.elibrary.core.unicomponent.action.BtnExportAction;
import com.javafee.elibrary.core.unicomponent.action.BtnImportAction;
import com.javafee.elibrary.core.unicomponent.border.CustomTitledBorder;
import com.javafee.elibrary.core.unicomponent.jscrollpain.CustomJScrollPane;
import com.javafee.elibrary.core.unicomponent.jtable.imortexportable.ImportExportableJTable;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ClientLoanPanel extends BasePanel {
    @Getter
    private ImportExportableJTable activeClientLoanTable;
    @Getter
    private ImportExportableJTable historyClientLoanTable;

    public ClientLoanPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        JPanel activeClientLoanPanel = new BasePanel();
        activeClientLoanPanel.setBorder(new CustomTitledBorder(null, SystemProperties.getInstance().getResourceBundle()
                .getString("clientLoanPanel.activeClientLoanPanelBorderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        add(activeClientLoanPanel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        activeClientLoanPanel.setLayout(gbl_panel);

        JScrollPane scrollPane = new CustomJScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        activeClientLoanPanel.add(scrollPane, gbc_scrollPane);

        activeClientLoanTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
                new ClientLoanTableModel(),
                true);
        activeClientLoanTable.setActions(new BtnImportAction(activeClientLoanTable), new BtnExportAction(activeClientLoanTable));
        scrollPane.setViewportView(activeClientLoanTable);

        JPanel historyClientLoanPanel = new BasePanel();
        historyClientLoanPanel.setBorder(new CustomTitledBorder(null, SystemProperties.getInstance().getResourceBundle()
                .getString("clientLoanPanel.historyClientLoanPanelBorderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;
        add(historyClientLoanPanel, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        historyClientLoanPanel.setLayout(gbl_panel_1);

        JScrollPane scrollPane_1 = new CustomJScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 0;
        historyClientLoanPanel.add(scrollPane_1, gbc_scrollPane_1);

        historyClientLoanTable = new ImportExportableJTable(Common.prepareIconListForExportImportComboBox(),
                new HistoryLoanTableModel(),
                true);
        historyClientLoanTable.setActions(new BtnImportAction(historyClientLoanTable), new BtnExportAction(historyClientLoanTable));
        scrollPane_1.setViewportView(historyClientLoanTable);
    }
}
