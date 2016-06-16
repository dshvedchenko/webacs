<%--
  Created by IntelliJ IDEA.
  User: dshvedchenko
  Date: 6/16/16
  Time: 6:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>ACS user account creation</title>
</head>
<body>

<sf:form method="post" modelAttribute="appuserDTO">
    <fieldset>
        <table cellspacing="0">
            <tr>
                <th><label for="user_username">Username : </label></th>
                <td><sf:input path="username" size="15" id="user_username"/>
                    <sf:errors path="username"/></td>
            </tr>
            <tr>
                <th><label for="user_password">Password : </label></th>
                <td><sf:input path="password" size="30" id="user_password"/>
                    <sf:errors path="password"/></td>
            </tr>
            <tr>
                <th><label for="user_password_confirm">Confirm password : </label></th>
                <td><sf:input path="confirmPassword" size="30" id="user_password_confirm"/></td>
            </tr>
            <tr>
                <th><label for="user_email">Username : </label></th>
                <td><sf:input path="email" size="64" id="user_email"/>
                    <sf:errors path="email"/></td>
            </tr>

        </table>
    </fieldset>
</sf:form>

</body>
</html>
