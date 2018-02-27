//package com.javafee.model;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.swing.event.TableModelEvent;
//import javax.swing.table.AbstractTableModel;
//
//import com.javafee.common.Constans.VolumeTableColumn;
//import com.javafee.common.SystemProperties;
//import com.javafee.hibernate.dao.HibernateUtil;
//import com.javafee.hibernate.dto.library.Client;
//import com.javafee.hibernate.dto.library.Volume;
//
//public class VolumeTableModel_old extends AbstractTableModel {
//	private static final long serialVersionUID = 1L;
//
//	protected static List<Volume> volumes;
//	private String[] columns;
//
//	public VolumeTableModel_old() {
//		super();
//		this.prepareHibernateDao();
//		this.columns = new String[] { "Tytu³",
//				"Autor",
//				"Kategoria",
//				"Wydawnictwo",
//				"Numer ISBN",
//				"Numer inwentarzowy",
//				"Iloœæ stron",
//				"Iloœæ egzemplarzy",
//				"Rezerwacje"
//		};
//	}
//
//	public Volume getVolume(int index) {
//		return volumes.get(index);
//	}
//
//	public void setVolume(int index, Volume volume) {
//		volumes.set(index, volume);
//	}
//
//	public void add(Volume volume) {
//		volumes.add(volume);
//		this.fireTableDataChanged();
//	}
//
//	public void remove(Volume volume) {
//		volumes.remove(volume);
//		this.fireTableDataChanged();
//	}
//	
//	public static void freshVolumes(){
//	volumes = HibernateUtil.getSession().createQuery("from Volume as vol join fetch vol.book").list();
//	}
//
//	@SuppressWarnings("unchecked")
//	protected void prepareHibernateDao() {
//		this.volumes = HibernateUtil.getSession().createQuery("from Volume as vol join fetch vol.book").list();
//		this.volumes = volumes.stream().filter(vol -> !vol.getIsLended()).collect(Collectors.toList());
//	}
//
//	@SuppressWarnings("unused")
//	private void executeUpdate(String entityName, Object object) {
//		HibernateUtil.beginTransaction();
//		HibernateUtil.getSession().update(entityName, object);
//		HibernateUtil.commitTransaction();
//	}
//
//	@Override
//	public int getColumnCount() {
//		return columns.length;
//	}
//
//	@Override
//	public int getRowCount() {
//		return volumes.size();
//	}
//
//	@Override
//	public Object getValueAt(int row, int col) {
//		Volume volume = volumes.get(row);
//		switch (VolumeTableColumn.getByNumber(col)) {
//		case COL_BOOK_TITLE:
//			return volume.getBook().getTitle();
//		case AUTHOR:
//			return volume.getBook().getAuthor();
//		case CATEGORY:
//			return volume.getBook().getCategory();
//		case PUBLISHING_HOUSE:
//			return volume.getBook().getPublishingHouse();
//		case NUMBER_ISBN:
//			return volume.getBook().getIsbnNumber();
//		case NUMBER_INVENTORY:
//			return volume.getInventoryNumber();
//		case NUMBERS_OF_PAGE:
//			return volume.getBook().getNumberOfPage();
//		case NUMBER_OF_COPIES:
//			return volume.getBook().getNumberOfTomes();
//		case LOANED:
//			return volume.getIsLended();
//		
//		default:
//			return null;
//		}
//	}
//
//	// @Override
//	// public void setValueAt(Object value, int row, int col) {
//	// Volume volume = volumes.get(row);
//	// Volume volumeShallowClone = (Volume) volume.clone();
//	//
//	// switch (VolumeTableColumn.getByNumber(col)) {
//	// case COL_BOOK:
//	// volumeShallowClone.setBook((Book) value);
//	// break;
//	// case COL_IS_READING_ROOM:
//	// volumeShallowClone.setIsReadingRoom((Boolean) value);
//	// break;
//	// }
//	//
//	// this.fireTableRowsUpdated(row, row);
//	// }
//
//	@Override
//	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//	}
//
//	@Override
//	public String getColumnName(int col) {
//		return columns[col];
//	}
//
//	@Override
//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		return false;
//	}
//
//	@Override
//	public void fireTableChanged(TableModelEvent e) {
//		super.fireTableChanged(e);
//	}
//}
