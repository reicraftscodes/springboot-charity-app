package com.nsa.charitystarter.data;

import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.sponsorship.SponsorshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class SponsorshipRepositoryJDBC implements SponsorshipRepository {
    private JdbcTemplate jdbc;
    private RowMapper<SponsorPageCreated> sponsorshipPageCreatedRowMapper;
    private RowMapper<SponsorDonationInfo> sponsorDonationStatsRowMapper;


    public SponsorshipRepositoryJDBC(JdbcTemplate aTemplate) {
        jdbc = aTemplate;
        sponsorshipPageCreatedRowMapper = (rs, i) -> new SponsorPageCreated(
                rs.getInt("id"),
                rs.getString("fundraiser_name"),
                rs.getString("charity_id"),
                rs.getString("fundraising_action"),
                rs.getTimestamp("date_created").toLocalDateTime(),
                rs.getTimestamp("first_valid_day").toLocalDateTime(),
                rs.getTimestamp("last_valid_day").toLocalDateTime(),
                rs.getString("furl")
        );

        sponsorDonationStatsRowMapper = (rs, i) -> new SponsorDonationInfo(
                rs.getString("fundraiser_name"),
                rs.getString("furl"),
                rs.getDouble("total_without_gift"),
                rs.getDouble("total_gift_aid")
        );
    }

    @Override
    public List<SponsorPageCreated> findByName(String name) {
        return jdbc.query("select id, fundraiser_name, charity_id," +
                " fundraising_action, date_created, first_valid_day, last_valid_day, furl" +
                " from sponsor_form where fundraiser_name like ?" +
                "", new Object[]{name}, sponsorshipPageCreatedRowMapper);
    }


    @Override
    public Optional<SponsorPageCreated> findByFURL(String furl) {
        try {
            return Optional.of(jdbc.queryForObject("select id, fundraiser_name, charity_id," +
                            " fundraising_action, date_created, first_valid_day, last_valid_day, furl" +
                            " from sponsor_form where furl = ?" +
                            "", new Object[]{furl},
                    sponsorshipPageCreatedRowMapper)
            );
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SponsorPageCreated> findByID(Integer ID) {
        try {
            return Optional.of(jdbc.queryForObject("select id, fundraiser_name, charity_id," +
                            " fundraising_action, date_created, first_valid_day, last_valid_day, furl" +
                            " from sponsor_form where id = ?" +
                            "", new Object[]{ID},
                    sponsorshipPageCreatedRowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<SponsorDonationInfo> getTop5SponsorshipsForACharity(Long charityID) {
        return jdbc.query("select sf.fundraiser_name, sum(d.amount_in_pence) as total_without_gift," +
                        " sum(gad.gift_aid_amount) as total_gift_aid,sf.furl from sponsor_form sf" +
                        " join donation d on (d.sponsor_form_id = sf.id)" +
                        "left join gift_aid_donation gad on (gad.sponsor_form_id = d.sponsor_form_id)" +
                        "where sf.charity_id = ? group by sf.fundraiser_name order by (sum(IFNULL(d.amount_in_pence,0)) + sum(IFNULL(gad.gift_aid_amount,0))) desc LIMIT 5",
                new Object[]{charityID},
                sponsorDonationStatsRowMapper);
    }

    @Override
    public List<SponsorDonationInfo> getRecentSponsorshipsForACharity(Long charityID) {
        return jdbc.query( "SELECT sf.fundraiser_name, sum(d.amount_in_pence) as total_without_gift," +
                        "sum(gad.gift_aid_amount) as total_gift_aid,sf.furl from sponsor_form sf "+
                        "join donation d on (d.sponsor_form_id = sf.id) "+
                        "left join gift_aid_donation gad on (gad.sponsor_form_id = d.sponsor_form_id) "+
                        "WHERE sf.CHARITY_ID = ? AND FURL NOT IN (" +
                        "select sf.furl " +
                        "from sponsor_form sf " +
                        "join donation d on (d.sponsor_form_id = sf.id) " +
                        "left join gift_aid_donation gad on (gad.sponsor_form_id = d.sponsor_form_id) " +
                        "where sf.charity_id = 1 group by sf.fundraiser_name order by (sum(IFNULL(d.amount_in_pence,0)) + sum(IFNULL(gad.gift_aid_amount,0))) desc LIMIT 5" +
                        ") " +
                        "group by sf.fundraiser_name " +
                        "ORDER BY DATE_CREATED DESC",
                new Object[]{charityID},
                sponsorDonationStatsRowMapper);
    }

    @Override
    public void saveSponsorshipEvent(SponsorPageCreated sponsorshipPageCreated) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("insert into sponsor_form(fundraiser_name, charity_id, " +
                                        "fundraising_action, date_created, first_valid_day, last_valid_day, furl) " +
                                        "values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, sponsorshipPageCreated.getFundraiserName());
                        ps.setInt(2, Integer.parseInt(sponsorshipPageCreated.getCharityID()));
                        ps.setString(3, sponsorshipPageCreated.getFundraisingAction());
                        ps.setDate(4, Date.valueOf(sponsorshipPageCreated.getDateCreated().toLocalDate()));
                        ps.setDate(5, Date.valueOf(sponsorshipPageCreated.getFirstValidDay().toLocalDate()));
                        ps.setDate(6, Date.valueOf(sponsorshipPageCreated.getLastValidDay().toLocalDate()));
                        ps.setString(7, sponsorshipPageCreated.getFurl());

                        return ps;
                    }
                },
                holder);

        log.debug("The new sponsor age furl " + sponsorshipPageCreated.getFurl());


    }

}