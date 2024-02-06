package com.underpressure.backend.controllers.classes.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.underpressure.backend.controllers.classes.dto.EntryDataDto;

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
