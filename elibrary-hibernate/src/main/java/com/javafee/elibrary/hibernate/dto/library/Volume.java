package com.javafee.elibrary.hibernate.dto.library;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "lend")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_book")
	private Book book;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "volume",
			cascade = {CascadeType.DETACH, CascadeType.MERGE,//
					CascadeType.PERSIST, CascadeType.REFRESH})
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
}
