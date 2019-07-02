package com.javafee.hibernate.dto.library;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NamedQueries({
		@NamedQuery(name = "Volume.checkIfInventoryNumberExist", query = "from Volume where inventoryNumber = :inventoryNumber")})
@Table(name = "lib_volume", uniqueConstraints = {@UniqueConstraint(columnNames = {"inventory_number"})})
@SequenceGenerator(name = "seq_lib_volume", sequenceName = "seq_lib_volume", allocationSize = 1)
public class Volume implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_volume")
	@Column(name = "id_volume", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idVolume;

	@Column(name = "inventory_number", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private String inventoryNumber;

	@Column(name = "state", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	private Character state;

	@Column(name = "is_reading_room", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isReadingRoom = false;

	@Column(name = "is_lended", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isLended = false;

	@Column(name = "is_reserve", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isReserve = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_book")
	private Book book;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "volume")
	private Set<Lend> lend = new HashSet<Lend>(0);

	@Column(name = "penalty_value", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private BigDecimal penaltyValue;

	@Override
	public Object clone() {
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		return "Volume [idVolume=" + idVolume + ", inventoryNumber=" + inventoryNumber + ", state=" + state
				+ ", isReadingRoom=" + isReadingRoom + ", isLended=" + isLended + ", isReserve=" + isReserve + ", book="
				+ book + ", lend=" + lend + ", penaltyValue=" + penaltyValue + "]";
	}

}
