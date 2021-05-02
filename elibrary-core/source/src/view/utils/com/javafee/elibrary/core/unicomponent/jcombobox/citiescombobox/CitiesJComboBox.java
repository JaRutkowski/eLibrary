package com.javafee.elibrary.core.unicomponent.jcombobox.citiescombobox;

import java.util.Comparator;
import java.util.function.Consumer;

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

	public void fetchNextDataPackage(Consumer reloadAction) {
		SystemProperties.fetchCitiesPackage(reloadAction);
	}

	public void fillComboBoxCity() {
		DefaultComboBoxModel<City> comboBoxCityModel = new DefaultComboBoxModel<>();
		Common.removeMoreComboBoxCityElementIfExists();
		Common.getCities().sort(Comparator.comparing(City::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		Common.prepareMoreComboBoxCityElement(Common.getCities());
		comboBoxCityModel.addAll(Common.getCities());
		setModel(comboBoxCityModel);
	}
}
