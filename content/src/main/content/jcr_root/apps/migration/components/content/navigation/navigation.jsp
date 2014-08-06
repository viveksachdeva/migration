<%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Top Navigation component

  Draws the top navigation

--%><%@include file="/libs/foundation/global.jsp"%><%
%><%@ page import="com.day.cq.commons.Doctype,
        com.day.cq.wcm.api.PageFilter,
        com.day.cq.wcm.api.PageManager,
        com.day.cq.wcm.foundation.Navigation,
        com.day.text.Text" %><%

	String startPath = properties.get("startPath", currentPage.getPath());
	int depth = properties.get("depth", 0);
	boolean showHome = properties.get("showHome", false);
	String home = Text.getAbsoluteParent(currentPage.getPath(), 2);
	int absParent = startPath == null ? 2 : pageManager.getPage(startPath).getDepth()-1;
	PageFilter filter = new PageFilter(request);
	Navigation nav = new Navigation(currentPage, absParent, filter, depth);
	String xs = Doctype.isXHTML(request) ? "/" : "";
	
	// help linkchecker to increase performance
	String linkCheckerHint = filter.isIncludeInvalid() ? "" : "x-cq-linkchecker=\"valid\"";

%>  
	    <ul id="nav">
			<% 
			if(showHome){
			%>
			  <li class="home"><a href="<%= home %>.html"><img src="/etc/designs/default/0.gif" alt="Home" <%=xs%>></a></li>
			<% 
			}
			for (Navigation.Element e: nav) {
			    switch (e.getType()) {
			       case NODE_OPEN:
			            %><ul><%
			            break;
			        case ITEM_BEGIN:
			            %><li <%= e.hasChildren() ? "class=\"noleaf\"" : "" %>><a href="<%= e.getPath() %>.html" <%= linkCheckerHint %>><%= e.getTitle() %></a><%
			            break;
			        case ITEM_END:
			            %></li><%
			            break;
			        case NODE_CLOSE:
			            %></ul><%
			            break;
			    }
			}
			%>
		    <%-- <li class="sep1">&nbsp;</li> --%>
		</ul>
