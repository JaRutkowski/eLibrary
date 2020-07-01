package com.javafee.elibrary.core.unicomponent.action;

import java.util.Optional;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.unicomponent.jcombobox.citiescombobox.CitiesJComboBox;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.Action;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.ActionWrapper;
import com.javafee.elibrary.hibernate.dto.association.City;

public class CitiesJComboBoxAction extends Action {
	private CitiesJComboBox citiesJComboBox;

	public CitiesJComboBoxAction(CitiesJComboBox citiesJComboBox) {
		super(null, null);
		setAction(new ActionWrapper(e -> onChangeCitiesJComboBox()));
		this.citiesJComboBox = citiesJComboBox;
	}

	public void onChangeCitiesJComboBox() {
		if (Optional.ofNullable(citiesJComboBox.getSelectedItem()).isPresent()
				&& Constants.APPLICATION_COMBO_BOX_MORE_OBJECT
				.equals(((City) citiesJComboBox.getSelectedItem()).getName()))
			citiesJComboBox.fetchNextDataPackage();
	}
}
