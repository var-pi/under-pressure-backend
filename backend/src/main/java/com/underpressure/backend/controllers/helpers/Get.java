package com.underpressure.backend.controllers.helpers;

import java.util.LinkedList;
import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.classes.request.data.EntryDataRowMapper;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.does_not_exist.TodaysEntryDoesNotExistException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.SubjectInstanceDoesNotExistsException;

public class Get {

    public static List<String> subjects(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT name FROM subjects";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public static Integer subjectId(String subjectName, JdbcTemplate jdbcTemplate) throws RequestException {
        String requestForSubjectId = "SELECT id FROM subjects WHERE name=?";

        try {
            return jdbcTemplate.queryForObject(requestForSubjectId, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    public static List<String> followedSubjects(String userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id=? AND if_followed=true";

        return jdbcTemplate.queryForList(sql, String.class, userId);
    }

    public static Integer subjectInstanceId(String userId, Integer subjectId, JdbcTemplate jdbcTemplate)
            throws RequestException {
        String sql = "SELECT id FROM subject_instances WHERE user_id=? AND subject_id=? AND is_followed=true";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectInstanceDoesNotExistsException();
        }

    }

    public static Integer todaysEntryId(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=? AND created_at=CURRENT_DATE";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);
        } catch (EmptyResultDataAccessException e) {
            throw new TodaysEntryDoesNotExistException(sql);
        }
    }

    public static List<EntryData> entries(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM entries WHERE subject_instance_id=?";

        return jdbcTemplate.query(sql, new EntryDataRowMapper(), subjectInstanceId);
    }
}
