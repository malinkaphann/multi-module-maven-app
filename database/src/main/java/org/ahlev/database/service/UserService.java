package org.ahlev.database.service;

import org.ahlev.database.exception.DuplicateException;
import org.ahlev.database.mapper.UserMapper;
import org.ahlev.database.model.User;
import org.ahlev.database.util.DbUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class UserService {

    final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void create(User user) {

        if(user == null) throw new IllegalArgumentException("input user is invaid");

        SqlSession session = DbUtil.getSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.create(user);
            session.commit();
        } catch(Exception e) {
            String errorMsg = e.getMessage();

            logger.error(errorMsg, e);
            session.rollback();

            if(DbUtil.isDuplicateException(errorMsg)) {
                Map<String, String> map = DbUtil.getDuplicateInfo(errorMsg);
                if(map != null) {
                    throw new DuplicateException(map.get("column"), map.get("value"));
                }
            }
            throw new RuntimeException(e);
        }
    }

    public User find(String name) {

        User returnedUser;

        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("input name is null or empty");
        }

        SqlSession session = DbUtil.getSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            returnedUser = mapper.find(name);
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return returnedUser;
    }

    public void clear() {
        SqlSession session = DbUtil.getSessionFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.clear();
            session.commit();
        } catch(Exception e) {
            logger.error(e.getMessage(), e);
            session.rollback();
            throw new RuntimeException(e);
        }
    }
}
