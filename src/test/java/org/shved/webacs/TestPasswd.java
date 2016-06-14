package org.shved.webacs;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * @author dshvedchenko on 6/14/16.
 */
public class TestPasswd {
    @Test
    public void checkPassword() {
        PasswordEncoder pw = new BCryptPasswordEncoder();
        System.out.println(pw.encode("1qaz2wsx"));

    }
}
