package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.Lend;

public interface LendRepository extends JpaRepository<Lend, Integer> {
	@Query("select len from Lend as len left join fetch len.reservation " +
			"where len.isReturned = false and " +
			"(len.reservation is null or (len.reservation is not null and (len.reservation.isActive = false or len.reservation.isCancelled = true))) " +
			"and len.client.idUserData != :idClient")
	List<Lend> findAllActiveLends(@Param("idClient") Integer idClient);
}
