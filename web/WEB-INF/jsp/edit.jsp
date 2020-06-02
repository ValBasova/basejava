<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <dl>
                <dt>${type.title}</dt>
                <dd>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                        <input type='text' name='${type.name()}' size=75 value='<%=((TextSection)section).getText()%>'>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getTextList())%></textarea>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizationList()%>"
                                   varStatus="counter">
                            <dl>
                                <dt>Название учереждения:</dt>
                                <dd><input type="text" name='${type}' size=100 value="${org.name}"></dd>
                            </dl>
                            <dl>
                                <dt>Сайт учереждения:</dt>
                                <dd><input type="text" name='${type}${counter.index}url' size=100 value="${org.url}"></dd>
                                </dd>
                            </dl>
                            <br>
                            <div style="margin-left: 30px">
                                <c:forEach var="pos" items="${org.positions}" varStatus="posCounter">
                                    <jsp:useBean id="pos" type="ru.javawebinar.basejava.model.Organization.Position"/>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}startDate${posCounter.index}" size=10
                                                   value="<%=pos.getTimeStart().format(DateTimeFormatter.ofPattern("uuuu-MM"))%>" placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}endDate${posCounter.index}" size=10
                                                   value="<%=pos.getTimeEnd().format(DateTimeFormatter.ofPattern("uuuu-MM"))%>" placeholder="MM/yyyy">
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${type}${counter.index}title${posCounter.index}' size=75
                                                   value="${pos.title}">
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${type}${counter.index}description" rows=5
                                                      cols=75>${pos.description}</textarea></dd>
                                    </dl>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose></dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
