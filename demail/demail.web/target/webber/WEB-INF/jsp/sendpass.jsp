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
                try {
                    if (login == "") throw "Login can't be empty";
                    var loginregexp = /^\w+$/g;
                    if (loginregexp.exec(login) == null) throw "Login must consist of letters, digits and _";
                    document.getElementById("gmailform").submit();
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
	<form id="gmailform" action="/webber/sendpass" method='POST'>
            <table>
                <tbody>
                    <tr>
                        <td></td>
                        <td>
                            <label id="errormessage"></label>
                            <c:if test="${not empty error}">
                                <label id="errorblock">
                                Cannot send password to your reserve mail
                                </label>
                            </c:if>
                            <c:if test="${not empty success}">
                                <label id="goodblock">
                                Password was sent
                                </label>
                            </c:if>                            
                            </td>
                    </tr>
                    <tr>
                        <td id="loginLabel" class="label">
                            Login:
                        </td>
                        <td id="loginInput">
                            <div>
                                <input id="username" name="username" type="text" class="inp">
                            </div>
                        </td>  
                    </tr>    
                    <tr>
                        <td></td>
                        <td>
                            <a class="entr" href="#" onclick="return postForm();">Send password</a>                            
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            Back to <a href="/webber/login">Login</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </section>
</body>
</html>
