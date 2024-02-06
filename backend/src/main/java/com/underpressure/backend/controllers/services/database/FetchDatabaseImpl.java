package com.underpressure.backend.controllers.services.database;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.controllers.classes.dto.EntryDataDto;
import com.underpressure.backend.controllers.classes.rowMappers.EntryDataRowMapper;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.SubjectInstanceDoesNotExistsException;
import com.underpressure.backend.exceptions.does_not_exist.TodaysEntryDoesNotExistException;

public class FetchDatabaseImpl implements FetchDatabase {

    JdbcTemplate jdbcTemplate;

    public FetchDatabaseImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> subjects() {
        String sql = "SELECT name FROM subjects";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public Integer subjectId(String subjectName) throws RequestException {
        String requestForSubjectId = "SELECT id FROM subjects WHERE name=?";

        try {
            return jdbcTemplate.queryForObject(requestForSubjectId, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    public List<String> followedSubjects(Integer userId) {
        String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id=? AND if_followed=true";

        return jdbcTemplate.queryForList(sql, String.class, userId);
    }

    public Integer subjectInstanceId(Integer userId, Integer subjectId)
            throws RequestException {
        String sql = "SELECT id FROM subject_instances WHERE user_id=? AND subject_id=?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectInstanceDoesNotExistsException();
        }

    }

    public Integer todaysEntryId(Integer subjectInstanceId) throws RequestException {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=? AND creation_date=CURRENT_DATE";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);
        } catch (EmptyResultDataAccessException e) {
            throw new TodaysEntryDoesNotExistException(sql);
        }
    }

    public List<EntryDataDto> entries(Integer subjectInstanceId) {
        String sql = "SELECT * FROM entries WHERE subject_instance_id=?";

        return jdbcTemplate.query(sql, new EntryDataRowMapper(), subjectInstanceId);
    }

}
