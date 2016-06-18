<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 6/15/16
  Time: 7:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div>
    <h2>Sign in to ACS</h2>
    <form method="post" class="signin" action="/login">
        <fieldset>
            <table cellspacing="0">
                <tr>
                    <th><label for="acs_username">Username</label></th>
                    <td><input id="acs_username"
                               name="acs_username"
                               type="text"/>
                        <small><a href="/user/registration"> Register now !</a></small>
                    </td>
                </tr>
                <tr>
                    <th><label for="acs_password">Password</label></th>
                    <td><input id="acs_password"
                               name="acs_password"
                               type="password"/>
                        <small><a href="/user/resend_password">Forgot?</a></small>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td><input id="remember_me"
                               name="_spring_security_remember_me"
                               type="checkbox"/>
                        <label for="remember_me"
                               class="inline">Remember me</label></td>
                </tr>
                <th></th>
                <td><input name="commit" type="submit" value="Sign In"/></td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        </fieldset>
    </form>
    <script type="text/javascript">
        document.getElementById('acs_username').focus();
    </script>
</div>


</body>
</html>
