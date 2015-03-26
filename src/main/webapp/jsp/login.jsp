<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page session="true"%>
<html>
<head>
    <title>Login Page</title>
    <style>
        .error {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }

        .msg {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            color: #31708f;
            background-color: #d9edf7;
            border-color: #bce8f1;
        }

        #login-box {
            width: 300px;
            padding: 20px;
            margin: 100px auto;
            background: #fff;
            -webkit-border-radius: 2px;
            -moz-border-radius: 2px;
            border: 1px solid #000;
        }
    </style>
</head>
<body onload='document.loginForm.username.focus();'>

<h1 style="text-align: center">***</h1>

<div id="login-box">

    <h3>Login with Username and Password</h3>

    <table style="border: dotted; color: #31708f"><tr><td>User demo</td><td>Login: user<br/>Password: user<br/></td></tr>
    <tr><td>Admin demo</td><td>Login: admin<br/>Password: admin</td></tr></table>

    <br/>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>


    <form
          action="<c:url value="/j_spring_security_check"/>" method="POST">

        <table>
            <tr>
                <td>User:</td>
                <td><input type="text" name='username' value="admin" /></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name='password' value="admin" /></td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"
                                       value="submit" />&nbsp;&nbsp;<input type="reset" value="reset"/> </td>
            </tr>
        </table>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />

    </form>
</div>

</body>
</html>