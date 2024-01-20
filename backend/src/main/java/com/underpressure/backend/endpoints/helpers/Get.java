package com.underpressure.backend.endpoints.helpers;

import java.util.LinkedList;
import java.util.List;

import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;

public class Get {

    public static Integer subjectId(String subjectName, JdbcTemplate jdbcTemplate) throws Exception {
        String requestForSubjectId = "SELECT id FROM subjects WHERE name='" + subjectName + "';";

        List<Integer> subjectIds = jdbcTemplate.queryForList(requestForSubjectId, Integer.class);

        if (subjectIds.size() == 0)
            throw new Exception("A subject with such name was not found.");

        return subjectIds.get(0);
    }

    public static List<String> subjects(String userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id='"
                + userId + "'";

        List<PGobject> PGobjects = jdbcTemplate.queryForList(sql, PGobject.class);

        List<String> subjectNames = new LinkedList<>();
        for (PGobject pgobject : PGobjects)
            subjectNames.add(pgobject.getValue());

        return subjectNames;
    }

}
