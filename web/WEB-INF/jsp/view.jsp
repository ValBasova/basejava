<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                          ru.javawebinar.basejava.model.AbstractSection>"/>
        <p>
        <c:choose>
            <c:when test="${sectionEntry.key == 'PERSONAL' || sectionEntry.key == 'OBJECTIVE'}">
                <h3><%=sectionEntry.getKey().getTitle() + " : "%>
                </h3>
                <ul>
                    <li><%=(((TextSection) sectionEntry.getValue()).getText())%>
                    </li>
                </ul>
            </c:when>
            <c:when test="${sectionEntry.key == 'ACHIEVEMENT' || sectionEntry.key == 'QUALIFICATIONS'}">
                <h3><%=sectionEntry.getKey().getTitle() + ": "%>
                </h3>
                <c:forEach var="text" items="<%=((ListSection) sectionEntry.getValue()).getTextList()%>">
                    <jsp:useBean id="text" type="java.lang.String"/>
                    <ul>
                        <li><%=text%>
                        </li>
                    </ul>
                </c:forEach>
            </c:when>
            <c:when test="${sectionEntry.key == 'EXPERIENCE' || sectionEntry.key == 'EDUCATION'}">
                <h3><%=sectionEntry.getKey().getTitle() + ": "%>
                <c:forEach var="orgn"
                           items="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizationList()%>">
                    <jsp:useBean id="orgn" type="ru.javawebinar.basejava.model.Organization"/>

                    </h3>
                    <c:choose>
                        <c:when test="${empty orgn.url}">
                            <h4>${orgn.name}</h4>
                        </c:when>
                        <c:otherwise>
                            <h4><a href="${orgn.url}">${orgn.name}</a></h4>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="pos" items="<%=(orgn.getPositions())%>">
                        <jsp:useBean id="pos" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <ul>
                            <li><%=pos.getTimeStart().format(DateTimeFormatter.ofPattern("MM.yyyy"))%>
                                <%=" - "%>
                                <%=pos.getTimeEnd().format(DateTimeFormatter.ofPattern("MM.yyyy"))%><br/>
                                <%=pos.getTitle()%><br/>
                                <%=pos.getDescription()%><br/></li>
                        </ul>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
        </p>
    </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>