package es.uniovi.sdi63.sdi2223entrega163.util;

import java.util.Map;

public class ParamFormatter {

    public static String format(Map<String, String[]> paramsMap) {
        StringBuilder params = new StringBuilder();
        for (var entry : paramsMap.entrySet()) {
            params.append(entry.getKey());
            params.append("=");
            params.append(String.join(",", entry.getValue()));
            params.append(",");
        }
        if (params.length() > 0) {
            params.setLength(params.length() - 1);
        }
        return params.toString();
    }

}
