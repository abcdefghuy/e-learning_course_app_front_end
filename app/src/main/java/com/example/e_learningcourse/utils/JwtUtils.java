package com.example.e_learningcourse.utils;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtUtils {
    public static long getExpirationTimeFromJWT(String jwtToken) {
        try {
            String[] splitToken = jwtToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(payload);
            return json.getLong("exp");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
