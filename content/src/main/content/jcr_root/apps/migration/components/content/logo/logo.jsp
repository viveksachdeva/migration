<%@include file="/apps/migration/global.jsp"%><%
%><%@ page import="com.day.text.Text,
                   com.day.cq.wcm.foundation.Image,
                   com.day.cq.commons.Doctype,
                   org.apache.commons.lang.StringUtils" %><%

    long absParent = properties.get("absParent", 2L);
    String home = Text.getAbsoluteParent(currentPage.getPath(), (int) absParent);
    String imagePath = properties.get("imageReference", "").equals("")?
    		properties.get("image",""):properties.get("imageReference","");
    %><a href="<%= home %>.html"><%
    if (!StringUtils.isNotBlank(imagePath)) {
        %>Home<%
    } else {%>
        <img alt="Home" src="<%= imagePath %>" />
    <%
    }
    %></a>