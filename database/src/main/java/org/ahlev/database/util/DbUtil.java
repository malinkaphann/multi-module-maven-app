package org.ahlev.database.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbUtil {

    private static final String CONFIG = "config.xml";

    private static SqlSessionFactory factory = null;

    private DbUtil() {}

    public static SqlSessionFactory getSessionFactory() {
        if(factory == null) {
            try {
                InputStream inputStream = Resources.getResourceAsStream(CONFIG);
                factory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return factory;
    }

    public static boolean isDuplicateException(String errorMsg) {
        if(errorMsg == null || errorMsg.isEmpty()) {
            throw new IllegalArgumentException("input errorMsg is null");
        }

        Pattern p = Pattern.compile("^Duplicate entry '(.*)' for key '(.*)'");
        Matcher m = p.matcher(errorMsg);
        return m.find();
    }

    public static Map<String, String> getDuplicateInfo(String msg) {
        if(msg == null || msg.isEmpty()) {
            throw new IllegalArgumentException("input msg is null");
        }

        Pattern p = Pattern.compile("^Duplicate entry '(.*)' for key '(.*)'");
        Matcher m = p.matcher(msg);
        if(m.find()) {
            Map<String, String> map = new HashMap<>();
            map.put("column", m.group(2));
            map.put("value", m.group(1));
            return map;
        }

        return null;
    }
}
