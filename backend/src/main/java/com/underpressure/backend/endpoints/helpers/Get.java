package com.underpressure.backend.endpoints.helpers;

import java.util.LinkedList;
import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.endpoints.exceptions.NotFoundException;

public class Get {

    public static List<String> subjects(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT name FROM subjects;";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public static Integer subjectId(String subjectName, JdbcTemplate jdbcTemplate) throws Exception {
        String requestForSubjectId = "SELECT id FROM subjects WHERE name='" + subjectName + "';";

        List<Integer> subjectIds = jdbcTemplate.queryForList(requestForSubjectId, Integer.class);

        if (subjectIds.size() == 0)
            throw new NotFoundException("A subject with such name was not found.");

        return subjectIds.get(0);
    }

    public static List<String> followedSubjects(String userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id='"
                + userId + "' AND if_followed=TRUE";

        List<PGobject> PGobjects = jdbcTemplate.queryForList(sql, PGobject.class);

        List<String> subjectNames = new LinkedList<>();
        for (PGobject pgobject : PGobjects)
            subjectNames.add(pgobject.getValue());

        return subjectNames;
    }

    public static Integer subjectInstanceId(String userId, Integer subjectId, JdbcTemplate jdbcTemplate)
            throws Exception {
        String sql = "SELECT id FROM subject_instances WHERE user_id='" + userId + "' AND subject_id=" + subjectId
                + ";";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class);

        if (ids.size() == 0)
            throw new NotFoundException("A subject_instance with such userId and subjectId was not found.");

        return ids.get(0);

    }

    public static Integer entryId(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=" + subjectInstanceId
                + " AND created_at=CURRENT_DATE;";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class);

        if (ids.size() == 0)
            throw new NotFoundException("An entry for this subjectInstance was not created yet today.");

        return ids.get(0);
    }
}
