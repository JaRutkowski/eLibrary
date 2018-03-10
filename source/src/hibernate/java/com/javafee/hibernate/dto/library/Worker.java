package com.javafee.hibernate.dto.library;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.javafee.hibernate.dto.common.UserData;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NamedQueries({ @NamedQuery(name = "Worker.checkIfWorkerLoginExist", query = "from Worker where login = :login"),
		@NamedQuery(name = "Worker.checkWithComparingIdIfClientLoginExist", query = "from Worker where login = :login and id != :id") })
@Table(name = "lib_worker")
@PrimaryKeyJoinColumn(name = "id_worker", referencedColumnName = "id_user_data")
public class Worker extends UserData implements Cloneable {
	@Column(name = "salary", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private BigDecimal salary;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pk.worker")
	private Set<LibraryWorker> libraryWorker = new HashSet<LibraryWorker>(0);

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
