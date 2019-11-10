package com.nsa.charitystarter.data;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.charity.CharityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CharityRepoJDBC implements CharityRepository {

    private JdbcTemplate jdbc;
    private RowMapper<Charity> charityMapper;

    @Autowired
    public CharityRepoJDBC(JdbcTemplate aTemplate) {
        jdbc = aTemplate;

        charityMapper = (rs, i) -> new Charity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("registration_id"),
                rs.getString("acronym"),
                rs.getString("purpose"),
                rs.getString("logo_file_name")
        );
    }

    @Override
    public Optional<Charity> findById(Integer id) {
//        return Optional.of(
//                jdbc.queryForObject(
//                        "select id, acronym, name, purpose, logo_file_name, registration_id from charity where id=?",
//                        new Object[]{id},
//                        charityMapper)
//        );
        try {
            return Optional.of(
                    jdbc.queryForObject(
                            "select id, acronym, name, purpose, logo_file_name, registration_id from charity where id=?",
                            new Object[]{id},
                            charityMapper)
            );
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
    @Override
    public List<Charity> findBySearch(String name) {
        String search = "%" + name + "%";
        return
                jdbc.query(
                        "select id, acronym, name, purpose, logo_file_name, registration_id  from charity where name like ?",
                        new Object[]{search},
                        charityMapper);
    }

}