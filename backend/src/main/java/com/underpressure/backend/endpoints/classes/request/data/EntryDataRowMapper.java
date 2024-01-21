package com.underpressure.backend.endpoints.classes.request.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EntryDataRowMapper implements RowMapper<EntryData> {

    @Override
    public EntryData mapRow(ResultSet rs, int rowNum) throws SQLException {

        EntryData entryData = new EntryData();
        entryData.setCreatedAt(rs.getDate("created_at"));
        entryData.setStressLevel(rs.getInt("stress_level"));

        return entryData;

    }
}
