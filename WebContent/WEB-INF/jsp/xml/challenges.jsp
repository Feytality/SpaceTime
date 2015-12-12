<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8"?>
<game>
<status>success</status>
<challenges page="${pageInt}" max="${max}">
<c:forEach items="${challenges}" var="challenge">
<challenge id="${challenge.id}" status="${challenge.getStatusInt()}"  version="${challenge.version}">
<challenger refid="${challenge.challenger.id}"/>
<challengee refid="${challenge.challengee.id}"/>
</challenge>
</c:forEach>
</challenges>
</game>