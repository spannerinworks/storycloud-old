<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, com.spannerinworks.storycloud.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Story Index</title>
</head>
<body>
<h1>Story Index</h1>

<ul> 
<% for (Story story : (List<Story>)request.getAttribute("stories")) { %>
    <li>
      <span><%= story.title %></span>
      <span><%= story.description %></span>
      <span><%= story.points %></span>
    </li>
<% } %>
</ul>
</body>
</html>