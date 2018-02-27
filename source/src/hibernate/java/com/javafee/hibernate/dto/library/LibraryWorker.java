package com.javafee.hibernate.dto.library;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@NamedQuery(name = "LibraryWorker.checkIfLibraryWorkerHiredExist", query = "from LibraryWorker where pk.worker.idUserData = :idWorker")
@Table(name = "lib_library_worker")
@AssociationOverrides({ @AssociationOverride(name = "pk.worker", joinColumns = @JoinColumn(name = "id_worker")),
		@AssociationOverride(name = "pk.libraryData", joinColumns = @JoinColumn(name = "id_library_data")) })
public class LibraryWorker {
	@EmbeddedId
	private LibraryWorkerId pk = new LibraryWorkerId();

	@Column(name = "is_accountant", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean isAccountant;

	public LibraryWorkerId getPk() {
		return pk;
	}

	public void setPk(LibraryWorkerId pk) {
		this.pk = pk;
	}

	@Transient
	public Worker getWorker() {
		return getPk().getWorker();
	}

	public void setWorker(Worker worker) {
		getPk().setWorker(worker);
	}

	@Transient
	public LibraryData getLibData() {
		return getPk().getLibraryData();
	}

	public void setLibData(LibraryData libraryData) {
		getPk().setLibraryData(libraryData);
	}

	public Boolean getIsAccountant() {
		return isAccountant;
	}

	public void setIsAccountant(Boolean isAccountant) {
		this.isAccountant = isAccountant;
	}
}
