package com.javafee.elibrary.web.rest.api.elibrarywebrestapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.domain.Lend;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.dto.LendDto;
import com.javafee.elibrary.web.rest.api.elibrarywebrestapi.model.repository.LendRepository;

@Service
public class ReservationService {
	@Autowired
	private LendRepository lendRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<LendDto> findAllActiveLends(Integer idClient) {
		return (lendRepository.findAllActiveLends(idClient)).stream().map(this::convertToLendDto).collect(Collectors.toList());
	}

	private LendDto convertToLendDto(Lend lend) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(lend, LendDto.class);
	}
}
