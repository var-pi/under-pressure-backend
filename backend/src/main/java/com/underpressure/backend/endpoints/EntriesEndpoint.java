package com.underpressure.backend.endpoints;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.ValidateProperty;

@RestController
public class EntriesEndpoint {

    public class EntryData {
        private Date createdAt;
        private Integer stressLevel;

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getStressLevel() {
            return stressLevel;
        }

        public void setStressLevel(Integer stressLevel) {
            this.stressLevel = stressLevel;
        }
    }

    private class EntryDataRowMapper implements RowMapper<EntryData> {

        @Override
        public EntryData mapRow(ResultSet rs, int rowNum) throws SQLException {

            EntryData entryData = new EntryData();
            entryData.setCreatedAt(rs.getDate("created_at"));
            entryData.setStressLevel(rs.getInt("stress_level"));

            return entryData;

        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/entries")
    @ResponseBody
    public Map<String, Object> dispatchEntries(@RequestBody Map<String, Object> requestData) {

        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");

        try {
            ValidateProperty.userId(userId, jdbcTemplate);
            ValidateProperty.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            String sql = "SELECT * FROM entries WHERE subject_instance_id=" + subjectInstanceId + ";";

            List<EntryData> entries = jdbcTemplate.query(sql, new EntryDataRowMapper());

            return FeedbackMap.create(true, "These are all the entries of this user for this subject", entries);
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage(), null);
        }
    }

}
