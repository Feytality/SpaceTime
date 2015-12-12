<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8"?>
<game>
<status>success</status>
<challenge id="${challenge.id}" status="${challenge.status}"  version="${challenge.version}">
<challenger refid="${challenge.challenger.id}"/>
<challengee refid="${challenge.challengee.id}"/>
</challenge>
</game>