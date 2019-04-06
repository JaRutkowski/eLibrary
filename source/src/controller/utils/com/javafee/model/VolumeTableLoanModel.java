package com.javafee.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.javafee.common.Constans.VolumeTableColumn;
import com.javafee.common.SystemProperties;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.library.Volume;

public class VolumeTableLoanModel extends VolumeTableModel {
	private static final long serialVersionUID = 1L;

	public VolumeTableLoanModel() {
		super();

		ArrayList<String> volumeLoanColumn = new ArrayList<>(Arrays.asList(columns));
		volumeLoanColumn
				.add(SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isReservedCol"));
		volumeLoanColumn
				.add(SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isLoanedCol"));
		columns = volumeLoanColumn.toArray(columns);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void prepareHibernateDao() {
		this.volumes = HibernateUtil.getSession().createQuery("from Volume as vol join fetch vol.book").list();
		this.volumes = volumes.stream().filter(vol -> !vol.getIsReadingRoom()).collect(Collectors.toList());
	}

	@Override
	public Object getValueAt(int row, int col) {
		Volume volume = volumes.get(row);
		switch (VolumeTableColumn.getByNumber(col)) {
			case COL_IS_RESERVERD:
				return volume.getIsReserve()
						? SystemProperties.getInstance().getResourceBundle()
						.getString("volumeTableModel.isReadigRoomTrueVal")
						: SystemProperties.getInstance().getResourceBundle()
						.getString("volumeTableModel.isReadigRoomFalseVal");
			case COL_IS_LOANED:
				return volume.getIsLended()
						? SystemProperties.getInstance().getResourceBundle()
						.getString("volumeTableModel.isReadigRoomTrueVal")
						: SystemProperties.getInstance().getResourceBundle()
						.getString("volumeTableModel.isReadigRoomFalseVal");
			default:
				return super.getValueAt(row, col);
		}
	}
}
