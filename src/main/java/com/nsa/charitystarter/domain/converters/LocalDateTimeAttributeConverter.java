package com.nsa.charitystarter.domain.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @see https://www.thoughts-on-java.org/persist-localdate-localdatetime-jpa/
 */

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDate) {
        return (locDate == null ? null : Timestamp.valueOf(locDate));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlDate) {
        return (sqlDate == null ? null : sqlDate.toLocalDateTime());
    }
}

