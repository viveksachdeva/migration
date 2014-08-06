<%@include file="/apps/migration/global.jsp"%>

<%@ page import="com.day.cq.commons.Doctype, com.day.cq.i18n.I18n, com.day.text.Text" %><%
%><%@include file="/libs/foundation/global.jsp" %><%
    I18n i18n = new I18n(slingRequest);

    String home = Text.getAbsoluteParent(currentPage.getPath(), 2);
    String xs = Doctype.isXHTML(request) ? "/" : "";
    long hierMod = sling.getService(com.day.cq.wcm.foundation.HierarchyModificationListener.class).getLastModified(home);

%>
<c:set var="locale">_${requestScope.language}</c:set>
<div class="header">
    <div class="headerLeft">
        logo
    </div>
    
    <div class="headerRight">
	   <div class="toptoolbar">
	       linklist userinf
	    </div> 
	    <div class="clear"></div>
	    <div class="search_area">
		    <ul>
		        <li>
		           Navigsion
		        </li>
		        <li>
				    <div class="q_search">
						<form action="<%= home %>/toolbar/search.html" method="get">
						    <fieldset>
						        <div class="input_box">
						            <label for="h-search-field" style="display: none;"><%= i18n.get("Enter your search query") %></label>
						            <input class="cq-auto-clear" type="text" value="<%= i18n.get("Enter Query") %>" name="q" <%= xs %> id="h-search-field">
						        </div>
						        <input type="button" onclick="this.form.submit();" class="btn" <%= xs %>>
						    </fieldset>
						</form>
				    </div>      
		        </li>
		    </ul> 
        </div>		    
	    <div class="clear"></div>
    </div>	    
    <cq:include script="topnav${locale}.jsp"/>
</div>