<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <title>DeMail: MailBox</title>
        <style>  
            @import url(/webber/css/common.css);
            @import url(/webber/css/createletter.css);  
        </style> 
        <script>
            function saveLetter() {
                post_to_url('/webber/mailbox/saveletter', {"subject":document.getElementById("subject").value,
                                                  "letterText":document.getElementById("letterText").value,
                                                  "to":document.getElementById("recipient").value});
            }

            function sendLetter() {
                var recipient = document.getElementById("recipient").value;
                try {
                    if (recipient == "") throw "No recipient found";
                    var recregexp = /^(\w+)@demail\.com$/g;
                    if (recregexp.exec(recipient) == null) throw "Wrong recipient format";
                    post_to_url('/webber/mailbox/sendletter', {"subject":document.getElementById("subject").value,
                                                  "letterText":document.getElementById("letterText").value,
                                                  "to":document.getElementById("recipient").value});
                } catch (err) {
                    document.getElementById("errormessage").innerHTML = err;
                    return false;
                }
            }    
    
            function post_to_url(path, params, method) {
                method = method || "post"; // Set method to post by default if not specified.

                // The rest of this code assumes you are not using a library.
                // It can be made less wordy if you use one.
                var form = document.createElement("form");
                form.setAttribute("method", method);
                form.setAttribute("action", path);

                for(var key in params) {
                    if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", key);
                        hiddenField.setAttribute("value", params[key]);

                        form.appendChild(hiddenField);
                     }
                }

                document.body.appendChild(form);
                form.submit();
            }
            
            
        </script>
    </head>
    <jsp:useBean id="sessionState" scope="session" class="demail.service.session.SessionState" />
    <body>
        
        <header>
            <%@ include file="/html/header.html" %>
        </header>
        <aside>
            <ul>
                <c:forEach var="directory" items="${sessionState.dirList}">
                    <li ondragover="allowLetterDrop(event)"
                        ondrop="dropLetter(event,'${directory.dirState}','${directory.id}')">
                        <a href="<c:out value="/webber/mailbox/${directory.name}" />" 
                           <c:if test="${sessionState.currentDir eq directory.name}">
                               style="background: #79a0c1; color: #fff;"
                           </c:if>>
                            <c:out value="${directory.name}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </aside>
        
        <section id="letterBlock">
            <nav>
                <ul>
                    <li>
                        <a id="sendLetter" href="#" onclick="sendLetter();return false;">
                            Send
                        </a>
                    </li>
                    <li>
                        <a id="saveLetter" href="#" onclick="saveLetter();return false;">
                            Save
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value="/j_spring_security_logout" />">
                            Logout
                        </a>
                    </li>
                </ul>
            </nav>
            <section>
                <table class="compose-head">
                    <tbody>
                        <tr>
                            <td></td>
                            <td id="errorblock">
                                <c:if test="${not empty error}">
                                    ${sessionState.errorText}
                                </c:if>
                                <label id="errormessage"></label>
                            </td>	
                        </tr>
                        <tr>
                        <tr>
                            <td id="toLabel">
                                To:
                            </td>
                            <td id="toInputArea">
                                <div>
                                    <input type="email" id="recipient" name="modelTo"
                                        <c:if test="${not empty sessionState.curTo}">
                                            value="${sessionState.curTo}"
                                        </c:if>
                                    >
                                </div>
                            </td>	
                        </tr>
                        <tr>
                            <td id="subjLabel">
                                Subject:
                            </td>
                            <td id="subjInputArea">
                                <div>
                                    <input type="text" id="subject" name="modelSubj"
                                        <c:if test="${not empty sessionState.curSubj}">
                                            value="${sessionState.curSubj}"
                                        </c:if>
                                    >
                                </div>
                            </td>	
                        </tr>
                    </tbody>
                </table>
                <textarea id="letterText" name="modelText"><c:if test="${not empty sessionState.curText}">${sessionState.curText}</c:if></textarea>
            </section>
        </section>
    </body>
</html>
