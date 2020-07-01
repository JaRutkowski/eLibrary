package com.javafee.elibrary.core.unicomponent.jcombobox.citiescombobox;

import javax.swing.DefaultComboBoxModel;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.unicomponent.jcombobox.CustomJComboBox;
import com.javafee.elibrary.core.unicomponent.jtable.actiontable.Action;
import com.javafee.elibrary.hibernate.dto.association.City;

public class CitiesJComboBox<E> extends CustomJComboBox {
	public CitiesJComboBox() {
		super();
		fillComboBoxCity();
	}

	public void setAction(Action citiesJComboBoxAction) {
		this.addActionListener(citiesJComboBoxAction.getAction());
	}

	public void fetchNextDataPackage() {
		SystemProperties.getInstance().fetchCitiesPackage();
		fillComboBoxCity();
	}

	private void fillComboBoxCity() {
		DefaultComboBoxModel<City> comboBoxCityModel = new DefaultComboBoxModel<>();
		comboBoxCityModel.addAll(Common.getCities());
		setModel(comboBoxCityModel);
	}
}
