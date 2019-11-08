package com.nsa.charitystarter.service.charity;

import com.nsa.charitystarter.domain.Charity;

import java.util.List;
import java.util.Optional;

public interface CharityRepository {
    public Optional<Charity> findById(Integer id);

    List<Charity> findBySearch(String searchTerm);

}
