package com.javafee.elibrary.core.tabbedform;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.action.IActionForm;

import lombok.Setter;

public class TabClientReservationEvent implements IActionForm {
	@Setter
	private TabbedForm tabbedForm;

	protected static TabClientReservationEvent clientReservationEvent = null;

	public TabClientReservationEvent(TabbedForm tabbedForm) {
		this.control(tabbedForm);
	}

	public static TabClientReservationEvent getInstance(TabbedForm tabbedForm) {
		if (clientReservationEvent == null) {
			clientReservationEvent = new TabClientReservationEvent(tabbedForm);
		}
		return clientReservationEvent;
	}

	public void control(TabbedForm tabbedForm) {
		setTabbedForm(tabbedForm);
		initializeForm();

		tabbedForm.getPanelClientReservations().getTabbedPane().addChangeListener(e -> onChangeTabbedPane());
	}

	@Override
	public void initializeForm() {
		initializeTabbedPane();
		reloadTabbedPane();
	}

	private void onChangeTabbedPane() {
		reloadTabbedPane();
	}

	private void initializeTabbedPane() {
		tabbedForm.getPanelClientReservations().getTabbedPane().addTab(
				com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString("clientReservationPanel.tabCreateReservationTitle"), null,
				tabbedForm.getPanelClientReservations().getCreateReservationPanel(), null);
		tabbedForm.getPanelClientReservations().getTabbedPane().addTab(
				com.javafee.elibrary.core.common.SystemProperties.getInstance().getResourceBundle().getString("clientReservationPanel.tabBrowseReservationTitle"), null,
				tabbedForm.getPanelClientReservations().getBrowseReservationPanel(), null);
	}

	private void reloadTabbedPane() {
		switch (Constants.Tab_ClientReservations.getByNumber(tabbedForm.getPanelClientReservations().getTabbedPane().getSelectedIndex())) {
			case TAB_CREATE_RESERVATIONS:
				TabCreateReservationEvent.getInstance(tabbedForm.getPanelClientReservations());
				break;
			case TAB_BROWSE_RESERVATIONS:
				TabBrowseReservationEvent.getInstance(tabbedForm.getPanelClientReservations());
				break;
			default:
				break;
		}
	}

	public static void clearDependentEvents() {
		TabCreateReservationEvent.tabCreateReservationEvent = null;
		TabBrowseReservationEvent.tabBrowseReservationEvent = null;
	}
}
