package com.underpressure.backend.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.underpressure.backend.responses.EntryDataDto;

public class EntryDataRowMapper implements RowMapper<EntryDataDto> {

    // * To convert from 'snake_case' to 'camelCase'.

    @Override
    public EntryDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        EntryDataDto entryData = new EntryDataDto();
        entryData.setCreationDate(rs.getDate("creation_date"));
        entryData.setStressLevel(rs.getInt("stress_level"));

        return entryData;

    }
}
