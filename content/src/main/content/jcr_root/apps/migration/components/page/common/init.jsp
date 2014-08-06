<%@include file="/apps/migration/global.jsp"%><%

Page languagePage = currentPage.getAbsoluteParent(2);
if(languagePage != null){
	request.setAttribute("language", languagePage.getName());	
}
%>