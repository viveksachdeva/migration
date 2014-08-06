<%@include file="/libs/foundation/global.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils,
                com.day.cq.wcm.api.WCMMode"%>

<%
String title = ""; 

if(!properties.get("title", "").equalsIgnoreCase("blank")){
    title = properties.get("title", String.class);
    if (!StringUtils.isNotBlank(title)) {
        title = resourcePage.getPageTitle();
    }
    if (!StringUtils.isNotBlank(title)) {
        title = resourcePage.getTitle();
    }
    if (!StringUtils.isNotBlank(title)) {
        title = resourcePage.getName();
    }
%>
    <div class="title"><%= title %></div>
<%    
}
if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
    out.println("[Use BLANK to ignore title to be displayed. "
                  + "By default page title will be displayed if no title is provided]");
}
%>