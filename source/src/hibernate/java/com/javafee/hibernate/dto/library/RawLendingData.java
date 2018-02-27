package com.javafee.hibernate.dto.library;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lib_raw_lending_data")
@SequenceGenerator(name = "seq_lib_raw_lending_data", sequenceName = "seq_lib_raw_lending_data", allocationSize = 1)
public class RawLendingData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lib_raw_lending_data")
	@Column(name = "id_lending_data", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer idLendingData;

	@Column(name = "lending_quantity", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	private Integer lendingQuantity;

	@Column(name = "summary_penalty_value", unique = false, nullable = true, insertable = true, updatable = true, precision = 9, scale = 2)
	private BigDecimal summaryPenaltyValue;

	@Temporal(TemporalType.DATE)
	@Column(name = "last_lendign_date", unique = false, nullable = true, insertable = true, updatable = true, length = 13)
	private Date lastLendingDate;

	@OneToOne
	@JoinColumn(name = "id_client")
	private Client client;

	public Integer getIdLendingData() {
		return idLendingData;
	}

	public void setIdLendingData(Integer idLendingData) {
		this.idLendingData = idLendingData;
	}

	public Integer getLendingQuantity() {
		return lendingQuantity;
	}

	public void setLendingQuantity(Integer lendingQuantity) {
		this.lendingQuantity = lendingQuantity;
	}

	public BigDecimal getSummaryPenaltyValue() {
		return summaryPenaltyValue;
	}

	public void setSummaryPenaltyValue(BigDecimal summaryPenaltyValue) {
		this.summaryPenaltyValue = summaryPenaltyValue;
	}

	public Date getLastLendingDate() {
		return lastLendingDate;
	}

	public void setLastLendingDate(Date lastLendingDate) {
		this.lastLendingDate = lastLendingDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
