package com.underpressure.backend.controllers.helpers;

import java.util.HashMap;
import java.util.Map;

public class FeedbackMap {

    public static Map<String, Object> create(Boolean ifSuccess, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", ifSuccess ? "success" : "fail");
        res.put("message", message);
        return res;
    }

    public static Map<String, Object> create(Boolean ifSuccess, String message, Object data) {
        Map<String, Object> res = create(ifSuccess, message);
        res.put("data", data);
        return res;
    }

}
