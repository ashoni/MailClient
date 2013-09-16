<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <title>DeMail: MailBox</title>
        <style>  
            @import url(/webber/css/common.css);
            @import url(/webber/css/mailbox.css);  
        </style> 
        <script>
            function deleteLetter() {
                var r=confirm("Are you sure you want to delete this letter?");
                if (r == true) {
                    post_to_url(document.URL+'/deleteletter', {});
                }
            }

            function createLetter(edit) {
                if (edit == "true") {
                    post_to_url('/webber/mailbox/createletter', {"edit": "true"});
                } else if (edit == "ans") {
                    post_to_url('/webber/mailbox/createletter', {"edit": "ans"});
                } else {
                    post_to_url('/webber/mailbox/createletter', {"edit": "false"});
                }
            }            
    
            function createDirectory() {
                var dir = prompt("Enter name:","NewDirectory");

                if (dir != null)
                {
                    post_to_url('/webber/mailbox/createdir', {"dirName":dir});
                }
            }
            
            function deleteDirectory() {
                if (${sessionState.curDir.dirState eq 'SYSTEM'}) {
                    alert("Only user directories can be removed");
                } else {
                    var r=confirm("Are you sure you want to delete ${sessionState.currentDir}?");
                    if (r == true) {
                        post_to_url('/webber/mailbox/deletedir', {});
                    }
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
            
            
            function dragLetter(event)
            {
                event.dataTransfer.setData("id",event.target.id);
                event.dataTransfer.setData("letter","true");
//                alert(event.dataTransfer.getData("id"));
//                alert(event.dataTransfer.getData("letter"));
            }
            function allowLetterDrop(event)
            {
//                if (folder==="USER"&&event.dataTransfer.getData("letter")==="true")
//                    {
                event.preventDefault();
//                    }
            }
            function dropLetter(event,folder,folderId)
            {
               if(!(event.dataTransfer.getData("letter")==="true"))
               {
                   return;
               }
               if (folder==="SYSTEM")
               {
                   alert("A letter cannot be moved to a system folder.");
                   return;
               }
               post_to_url('/webber/mailbox/moveletter', {"letterId":event.dataTransfer.getData("id"),
                                                  "toDirId":folderId});
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
        <section id="topBlock">
            <section id="letterlist">
                <ol id="letters" class="hfeed">
                    <c:forEach var="letterMeta" items="${sessionState.lettersList}" varStatus="status">
                        <li draggable="true" ondragstart="dragLetter(event)" id="${letterMeta.letterMetaInfo.letterId}"
                            <c:choose>
                                <c:when test="${not empty sessionState.letter && sessionState.letter.id == letterMeta.letterMetaInfo.letterId}">
                                        style="background: #fffacd;"
                                </c:when>
                                <c:when test="${letterMeta.letterMetaInfo.letterState eq 'RECEIVED'}">
                                        style="background: #d3f38c;"
                                </c:when>
                            </c:choose>
                        >
                            <article class="letterDescription">	
                                 <footer class="letter-info">
                                    <abbr class="published">
                                        <fmt:formatDate value="${letterMeta.date}" 
                                                        pattern="yyyy-MM-dd HH:mm:ss" />
                                    </abbr>
                                    <address class="vcard author">
                                        <c:choose>
                                            <c:when test="${letterMeta.letterMetaInfo.letterState eq 'SENT'}">
                                                To ${letterMeta.letterMetaInfo.partnerMailAddress}
                                            </c:when>
                                            <c:when test="${letterMeta.letterMetaInfo.letterState eq 'SAVED'}">
                                                ${letterMeta.letterMetaInfo.partnerMailAddress}
                                            </c:when>    
                                            <c:otherwise>
                                                From ${letterMeta.letterMetaInfo.partnerMailAddress}
                                            </c:otherwise> 
                                        </c:choose>
                                    </address>
                                </footer>
                                <div class="entry-content">
                                    <a href="<c:out value="/webber/mailbox/${sessionState.currentDir}/${letterMeta.letterMetaInfo.letterId}" />">
                                        <c:choose>
                                        <c:when test="${empty letterMeta.subject}">
                                            (no subject)
                                        </c:when>
                                        <c:otherwise>
                                            ${letterMeta.subject}
                                        </c:otherwise>
                                      </c:choose>
                                    </a>
                                </div>
                            </article>
                        </li>
                    </c:forEach>
                </ol>
            </section>
            <section id="letterPlace">
                <nav>
                    <ul>
                        <li>
                            <a id="createLetter" href="#" onclick="createLetter('false');return false;" class="withLetter">
                                Compose
                            </a>
                        </li>
                        <li>
                            <c:if test="${not empty sessionState.letter}">
                                <a id="deleteLetter" href="#" onclick="deleteLetter();return false;" class="withLetter">
                                    Delete
                                </a>
                            </c:if>  
                            <c:if test="${empty sessionState.letter}">
                                <label style="color: #808080">
                                    Delete
                                </label>
                            </c:if>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${sessionState.currentDir eq 'Drafts' and not empty sessionState.letter}">
                                    <a id="editLetter" href="#" onclick="createLetter('true');return false;" class="withLetter">
                                        Edit
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <label style="color: #808080">
                                        Edit
                                    </label>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <c:if test="${not empty sessionState.letter}">
                                <a id="answerLetter" href="#" onclick="createLetter('ans');return false;" class="withLetter">
                                    Answer
                                </a>
                            </c:if>  
                            <c:if test="${empty sessionState.letter}">
                                <label style="color: #808080">
                                    Answer
                                </label>
                            </c:if>
                        </li>
                        <li>
                            <a id="createDir" href="#" onclick="createDirectory();return false;">
                                Create Directory
                            </a>
                        </li>
                        <li>
                            <a id="deleteDir" href="#" onclick="deleteDirectory();return false;">
                                Delete Directory
                            </a>
                        </li>
                        <li>
                            <a id="refresh" href="/webber/mailbox/${sessionState.currentDir}">
                                Refresh
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/j_spring_security_logout" />">
                                Logout
                            </a>
                        </li>
                    </ul>
                </nav>
                <article id="letterText">
                    <c:if test="${not empty sessionState.letter}">
                        ${sessionState.letter.body}
                    </c:if>        
                </article>
            </section>
        </section>
    </body>
</html>
