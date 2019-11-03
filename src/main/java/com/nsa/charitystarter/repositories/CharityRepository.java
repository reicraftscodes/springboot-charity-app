//package com.nsa.charitystarter.repositories;
//
//import com.nsa.charitystarter.domain.models.Charity;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface CharityRepository {
//    public Optional<Charity> findById(Long id); //change parameter to a Long.  JPA convention that it matches the type of the @Id
//
//    public List<Charity> findBySearch(String searchTerm);
//
//    public List<Charity> findByName(String name);
//
//    public List<Charity> findByNameContaining(String name);
//}
