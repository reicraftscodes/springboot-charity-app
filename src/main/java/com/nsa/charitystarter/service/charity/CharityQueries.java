package com.nsa.charitystarter.service.charity;


import com.nsa.charitystarter.domain.Charity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharityQueries implements CharityFinder {

    private CharityRepository charityRepository;

    private List<Charity> charities = new ArrayList<>();

    public CharityQueries(CharityRepository repo) {
        charityRepository = repo;

    }

    public Optional<Charity> findCharityByIndex(Integer index) {
        return charityRepository.findById(index);
    }

    public List<Charity> findCharityBySearch(String searchTerm) {
        return charityRepository.findBySearch(searchTerm);
    }
}
