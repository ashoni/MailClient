<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>DeMail: Registration</title>
        <style>  
            @import url(css/common.css);  
            @import url(css/reg.css);  
        </style> 
        <script>
            function postForm() {
                document.getElementById("errormessage").innerHTML = ""; 
                var name = document.getElementById("name").value;
                var surname = document.getElementById("surname").value;
                var phone = document.getElementById("phone").value;
                var reserveMailAddress = document.getElementById("reserveMailAddress").value;
                var login = document.getElementById("mailAddress").value;
                var pass = document.getElementById("password").value;
                try {
                    var nameregexp = /^[A-Za-z]+$/g;
                    if (name == "") throw "Name can't be empty";
                    if (nameregexp.exec(name) == null) throw "Name must consist of letters";
                    var surnameregexp = /^[A-Za-z]+$/g;
                    if (surname == "") throw "Surname can't be empty";
                    if (surnameregexp.exec(surname) == null) throw "Surname must consist of letters";
                    var phoneregexp = /^\d{3,}$/g;
                    if (phone == "") throw "Phone can't be empty";
                    if (phoneregexp.exec(phone) == null) throw "Phone must consist of digits";
                    var mailregexp = /(^\w+@\w+\.\w+$)|(^$)/g;
                    if (mailregexp.exec(reserveMailAddress) == null) throw "Reserved mail isn't valid";                    
                    if (login == "") throw "Login can't be empty";
                    var loginregexp = /^\w+$/g;
                    if (loginregexp.exec(login) == null) throw "Login must consist of letters, digits and _";
                    if (pass == "") throw "Password can't be empty";
                    var passregexp = /^\w{4,}$/g;
                    if (passregexp.exec(pass) == null) throw "Password must consist of at least 4 letters, digits and _";
                    document.getElementById("regform").submit();
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

        <div id="list">
        <form method="post" action="/webber/registration" id="regform">
            <table class="registration">
                <tbody>
                    <tr>
                        <td></td>
                        <td id="errorblock">
                            <c:if test="${not empty error}">
                                Your registration attempt was not successful, try again
                            </c:if>
                            <c:if test="${not empty repeat}">
                                Choose another login
                            </c:if>    
                            <label id="errormessage"></label>    
                        </td>
                    </tr>
                    <tr>
                        <td id="nameLabel" class="label">
                            Name:
                        </td>
                        <td id="nameInput">
                            <div>
                                <input type="text" id="name" name="name" class="inp" value="${user.name}"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td id="surnameLabel" class="label">
                            Surname:
                        </td>
                        <td id="surnameInput">
                            <div>
                                <input type="text" id="surname" name="surname" class="inp" value="${user.surname}"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td id="phoneLabel" class="label">
                            Phone:
                        </td>
                        <td id="phoneInput">
                            <div>
                                <input type="tel" id="phone" name="phone" class="inp" value="${user.phone}"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td id="dateLabel" class="label">
                            Date of Birth:
                        </td>
                        <td id="dateInput">
                            <input type="date" id="dateOfBirth" name="dateOfBirth" class="inp" value=${user.dateOfBirth}/>
                        </td>
                    </tr>
                    <tr>
                        <td id="reserveLabel" class="label">
                            Reserved Mail:
                        </td>
                        <td id="reserveInput">
                            <div>
                                <input type="email" class="inp" name="reserveMailAddress" id="reserveMailAddress" value="${addr.reserveMailAddress}"/>
                            </div>
                        </td>  
                    </tr>
                    <tr>
                        <td id="loginLabel" class="label">
                            Login:
                        </td>
                        <td id="loginInput">
                            <div>
                                <input id="mailAddress" class="inp" name="mailAddress" value="${addr.mailAddress}"/>
                            </div>
                        </td>  
                    </tr>    
                    <tr>
                        <td id="passLabel" class="label">
                            Password:
                        </td>
                        <td id="passInput">
                            <div>
                                <input type="password" id="password" name="password" class="inp" value="${addr.password}"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <a class="entr" href="#" onclick="return postForm();">Register</a> 
                            or <a href="/webber/login">Login</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
    </body>
</html>
