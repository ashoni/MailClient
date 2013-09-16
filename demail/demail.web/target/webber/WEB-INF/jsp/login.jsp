<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>DeMail: Login</title>
        <style>  
            @import url(css/common.css);  
            @import url(css/login.css);  
        </style> 
        <script>
            function postForm() {
                document.getElementById("errormessage").innerHTML = "";               
                var login = document.getElementById("username").value;
                var pass = document.getElementById("password").value;
                try {
                    if (login == "") throw "Login can't be empty";
                    var loginregexp = /^\w+$/g;
                    if (loginregexp.exec(login) == null) throw "Login must consist of letters, digits and _";
                    if (pass == "") throw "Password can't be empty";
                    var passregexp = /^\w{4,}$/g;
                    if (passregexp.exec(pass) == null) throw "Password must consist of at least 4 letters, digits and _";
                    document.getElementById("logform").submit();
                    return false;
                } catch(err) {
                    document.getElementById("errormessage").innerHTML = err;
                    return false;
                }
            }
        </script>
    </head>
    
    <body>
        <header>
            <%@ include file="/html/header.html" %>
        </header>

        
    <section class="loginform">  
	<form id="logform" action="<c:url value='j_spring_security_check'/>" method='POST'>
            <table>
                <tbody>
                    <tr>
                        <td></td>
                        <td id="errorblock">
                            <c:if test="${not empty error}">
                                Your login attempt was not successful, try again
                            </c:if>
                            <label id="errormessage"></label>    
                        </td>
                    </tr>
                    <tr>
                        <td id="loginLabel" class="label">
                            Login:
                        </td>
                        <td id="loginInput">
                            <div>
                                <input id="username" type="text" name="j_username" class="inp" placeholder="" required>
                            </div>
                        </td>  
                    </tr>    
                    <tr>
                        <td id="passLabel" class="label">
                            Password:
                        </td>
                        <td id="passInput">
                            <div>
                                <input id="password" type="password" name="j_password" class="inp" placeholder="" required>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <a class="entr" href="#" onclick="return postForm();">Login</a>
                            or <a href="/webber/registration">Register</a>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <a href="/webber/sendpass">Forgot your password?</a>
                        </td>
                    </tr>
                </tbody>
            </table>
            
            
        </form>
    </section>
</body>
</html>
