package org.shved.webacs;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractTransactionalProfileTest extends AbstractProfileTest {

    /**
     * Set the default user on the security context.
     */
    protected void doInit() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("", ""));
    }

}
