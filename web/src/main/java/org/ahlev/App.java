package org.ahlev;

import org.ahlev.database.model.User;
import org.ahlev.database.service.UserService;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        User user = new User("ahlev");

        UserService service = new UserService();

        service.create(user);

        get("/hello", (req, res) -> "Hello World");
    }
}
