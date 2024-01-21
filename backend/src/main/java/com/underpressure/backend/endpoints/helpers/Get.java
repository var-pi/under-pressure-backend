package com.underpressure.backend.endpoints.helpers;

import java.util.LinkedList;
import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.endpoints.classes.request.data.EntryData;
import com.underpressure.backend.endpoints.classes.request.data.EntryDataRowMapper;
import com.underpressure.backend.endpoints.exceptions.NotFoundException;

public class Get {

    public static List<String> subjects(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT name FROM subjects";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public static Integer subjectId(String subjectName, JdbcTemplate jdbcTemplate) throws Exception {
        String requestForSubjectId = "SELECT id FROM subjects WHERE name=?";

        try {
            return jdbcTemplate.queryForObject(requestForSubjectId, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("There are no subjects with this name.");
        }
    }

    public static List<String> followedSubjects(String userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id=? AND if_followed=TRUE";

        List<PGobject> PGobjects = jdbcTemplate.queryForList(sql, PGobject.class, userId);

        List<String> subjectNames = new LinkedList<>();
        for (PGobject pgobject : PGobjects)
            subjectNames.add(pgobject.getValue());

        return subjectNames;
    }

    public static Integer subjectInstanceId(String userId, Integer subjectId, JdbcTemplate jdbcTemplate)
            throws Exception {
        String sql = "SELECT id FROM subject_instances WHERE user_id=? AND subject_id=?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("A subject_instance with given userId and subject_id doesn't exists.");
        }

    }

    public static Integer entryId(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=? AND created_at=CURRENT_DATE";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("An entry hasn't yet been aadded today for this subject_instance.");
        }
    }

    public static List<EntryData> entries(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM entries WHERE subject_instance_id=?";

        return jdbcTemplate.query(sql, new EntryDataRowMapper(), subjectInstanceId);
    }
}
