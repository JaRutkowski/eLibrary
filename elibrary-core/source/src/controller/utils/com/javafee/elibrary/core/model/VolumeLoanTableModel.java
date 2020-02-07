package com.javafee.elibrary.core.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import com.javafee.elibrary.core.common.Constants;
import com.javafee.elibrary.core.common.Constants.VolumeTableColumn;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.startform.LogInEvent;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dto.library.Volume;

public class VolumeLoanTableModel extends VolumeTableModel {
	private static final long serialVersionUID = 1L;

	public VolumeLoanTableModel() {
		super();
		ArrayList<String> volumeLoanColumn = new ArrayList<>(Arrays.asList(columns));
		volumeLoanColumn.add(SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isReservedCol"));
		volumeLoanColumn.add(SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isLoanedCol"));
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
						.getString("volumeTableModel.isReadingRoomTrueVal")
						: SystemProperties.getInstance().getResourceBundle()
						.getString("volumeTableModel.isReadingRoomFalseVal");
			case COL_IS_LOANED:
				return getValueColIsLoanedBaseOnRole(volume);
			default:
				return super.getValueAt(row, col);
		}
	}

	private Object getValueColIsLoanedBaseOnRole(Volume volume) {
		boolean isLent = !Objects.isNull(volume.getLend()) && volume.getLend().stream()
				.filter(l -> !l.getIsReturned() && volume.getIdVolume().equals(l.getVolume().getIdVolume()))
				.findAny().isPresent();
		String lendToDate = isLent ? Constants.APPLICATION_DATE_FORMAT.format(volume.getLend().stream().filter(l -> !l.getIsReturned()).findFirst().get().getReturnedDate()) : "";
		Object result = LogInEvent.getRole() == Constants.Role.CLIENT
				? (isLent ? MessageFormat.format(SystemProperties.getInstance().getResourceBundle()
				.getString("volumeTableModel.isLentWithDateTrueVal"), lendToDate)
				: SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isReadingRoomFalseVal"))
				: isLent ? SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isReadingRoomTrueVal")
				: SystemProperties.getInstance().getResourceBundle().getString("volumeTableModel.isReadingRoomFalseVal");
		return result;
	}
}
