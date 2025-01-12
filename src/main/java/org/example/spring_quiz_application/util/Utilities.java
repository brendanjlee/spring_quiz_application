package org.example.spring_quiz_application.util;

import org.example.spring_quiz_application.DTO.UserDTO;
import org.example.spring_quiz_application.model.User;

public class Utilities {
    public static void logApi(String baseurl, String path) {
        System.out.println(baseurl + "/" + path);
    }

    public static void logApi(String baseurl, String path, String... params) {
        StringBuilder sb = new StringBuilder(baseurl)
                .append("/")
                .append(path);

        if (params != null && params.length > 0) {
            sb.append("- Params: ");
            for (String param : params) {
                sb.append(param).append(", ");
            }

            sb.setLength(sb.length() - 2);
        }
        System.out.println(sb.toString());
    }

    public static void logApiWithMethod(String method, String baseurl,
                                        String path, String... params) {
        StringBuilder sb = new StringBuilder(method)
                .append("  ")
                .append(baseurl)
                .append("/")
                .append(path);

        if (params != null && params.length > 0) {
            sb.append("- Params: ");
            for (String param : params) {
                sb.append(param).append(", ");
            }

            sb.setLength(sb.length() - 2);
        }
        System.out.println(sb.toString());
    }
}
