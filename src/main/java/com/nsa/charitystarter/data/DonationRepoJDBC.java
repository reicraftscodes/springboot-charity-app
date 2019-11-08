package com.nsa.charitystarter.data;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.service.donation.DonationRepository;
import com.nsa.charitystarter.service.events.DonationMade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.round;

@Slf4j
@Repository
public class DonationRepoJDBC implements DonationRepository {
    private JdbcOperations jdbc;

    public DonationRepoJDBC(JdbcOperations aJdbc) {
        jdbc = aJdbc;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    @Override
    public void saveDonationEvent(DonationMade donationEvent) {
        Long addressId = saveAddress(donationEvent.getDonorAddress1(), donationEvent.getDonorAddress2(), donationEvent.getDonorCity(),
                donationEvent.getDonorCountry(), donationEvent.getDonorPostcode());
        Long donorId = saveDonor(donationEvent.getDonorName(), addressId);
        Long donationId = saveDonation(donationEvent.getDonationAmount(), donationEvent.getDonationTime(),
                donationEvent.getIsGiftAidEligible(), donationEvent.getSponsor_form_id(),
                donationEvent.getTheCharity(), donorId);
        log.debug("The new donation key is " + donationId);
    }

    @Override
    public List<DonationSummary> getLastDonationsBySponsor(Integer sponsorId, Integer numberOfDonations) {

        RowMapper<DonationSummary> donationMapper = (rs, i) -> new DonationSummary(
                rs.getString("first_name"),
                rs.getString("last_name"),
                round(rs.getDouble("amount_in_pence") / 100, 2),
                rs.getTimestamp("donation_date").toLocalDateTime()
        );

        return
                jdbc.query(
                        "SELECT amount_in_pence, donation_date, donor.First_Name,  donor.Last_Name FROM  donation " +
                                "JOIN donor on (donation.donor_Id = donor.Id)  where sponsor_form_id = ? " +
                                "order by donation_date desc limit ?",
                        new Object[]{sponsorId, numberOfDonations},
                        donationMapper);
    }

    @Override
    public Double getTotalDonationAmount(Integer sponsorId) {

        Double result;

        result =
                jdbc.queryForObject(
                        "SELECT Sum(amount_in_pence) FROM donation where sponsor_form_id = ? ",
                        new Object[]{sponsorId}
                        , Double.class);

        return result == null ? 0d : round(result / 100D, 2);
    }

    @Override
    public Double getTotalGiftAidDonationAmount(Integer sponsorId) {

        Double result;

        result =
                jdbc.queryForObject(
                        "SELECT Sum(gift_aid_amount) FROM gift_aid_donation where sponsor_form_id = ? ",
                        new Object[]{sponsorId}
                        , Double.class);

        return result == null ? 0d : round(result / 100D, 2);
    }

    private Long saveAddress(String donorAddress1, String donorAddress2, String donorCity, String donorCountry, String donorPostcode) {

        //Need an object to manage the keys that are generated

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbc.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(
                                        "insert into address(street, district, city, postal_code, country_iso_code) " +
                                                "values (?,?,?,?,?)"
                                        , Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, donorAddress1);
                        ps.setString(2, donorAddress2);
                        ps.setString(3, donorCity);
                        ps.setString(4, donorPostcode);
                        ps.setString(5, donorCountry);
                        return ps;
                    }
                },
                holder);

        return holder.getKey().longValue();

    }

    private Long saveDonor(String donorName, Long addressId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        String[] names = donorName.split(" ");
        String firstName = names[0];
        String lastName = names[names.length - 1];

        jdbc.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(
                                        "insert into donor(first_name, last_name, address_id) " +
                                                "values (?,?,?)",
                                        Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, firstName);
                        ps.setString(2, lastName);
                        ps.setLong(3, addressId);
                        return ps;
                    }
                },
                holder);

        return holder.getKey().longValue();

    }

    private Long saveDonation(Double donationAmount,
                              LocalDateTime donationTime,
                              Boolean isGiftAidEligible,
                              Integer sponsorFormID,
                              Charity theCharity,
                              Long donorId) {

        GeneratedKeyHolder holder = new GeneratedKeyHolder();


        jdbc.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("insert into donation(amount_in_pence, donation_date, is_own_money, " +
                                        "has_no_benefit_to_donor, wishes_to_gift_aid, donor_id, sponsor_form_id, charity_id) " +
                                        "values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);


                        ps.setLong(1, Math.round(donationAmount * 100));
                        ps.setDate(2, Date.valueOf(donationTime.toLocalDate()));
                        ps.setBoolean(3, isGiftAidEligible);
                        ps.setBoolean(4, isGiftAidEligible);
                        ps.setBoolean(5, isGiftAidEligible);
                        ps.setLong(6, donorId);
                        ps.setInt(7, sponsorFormID);
                        ps.setLong(8, theCharity.getId());
                        return ps;
                    }
                },
                holder);

        return holder.getKey().longValue();
    }
}
