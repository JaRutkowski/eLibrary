package com.javafee.hibernate.dto.library;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = "pk")
@NamedQuery(name = "LibraryWorker.checkIfLibraryWorkerHiredExist", query = "from LibraryWorker where pk.worker.idUserData = :idWorker")
@Table(name = "lib_library_worker")
@AssociationOverrides({@AssociationOverride(name = "pk.worker", joinColumns = @JoinColumn(name = "id_worker")),
		@AssociationOverride(name = "pk.libraryData", joinColumns = @JoinColumn(name = "id_library_data"))})
public class LibraryWorker implements Cloneable {
	@EmbeddedId
	private LibraryWorkerId pk = new LibraryWorkerId();

	@Column(name = "is_accountant", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isAccountant;

	@Transient
	public Worker getWorker() {
		return getPk().getWorker();
	}

	public void setWorker(Worker worker) {
		getPk().setWorker(worker);
	}

	public void setLibraryData(LibraryData libraryData) {
		getPk().setLibraryData(libraryData);
	}

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
