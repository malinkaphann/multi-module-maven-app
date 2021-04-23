package org.ahlev.database;

import org.ahlev.database.model.User;
import org.ahlev.database.service.UserService;
import org.ahlev.database.util.DbUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Unit test for simple App.
 */
public class UserTest
{
    UserService service;

    @Before
    public void before() {
        service = new UserService();
        service.clear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_user_by_giving_a_null_param() {
        service.create(null);
    }

    @Test(expected = RuntimeException.class)
    public void test_create_duplicate_user() {
        User user = new User("ahlev");
        service.create(user);
        service.create(user);
    }

    @Test
    public void test_create_user_successfully() {
        User user = new User("ahlev");
        service.create(user);
    }

    @Test
    public void test_extracting_duplicate_info() {
        String errorMsg = "Duplicate entry 'ahlev' for key 'user.name'";
        Map<String, String> map = DbUtil.getDuplicateInfo(errorMsg);
        Assert.assertNotNull(map);
        Assert.assertEquals("user.name", map.get("column"));
        Assert.assertEquals("ahlev", map.get("value"));
    }

    @Test
    public void test_find_a_user() {
        String name = "ahlev";
        User user = new User(name);
        service.create(user);

        User findUser = service.find(name);
        Assert.assertNotNull(findUser);
        Assert.assertEquals(name, findUser.getName());
    }

}
