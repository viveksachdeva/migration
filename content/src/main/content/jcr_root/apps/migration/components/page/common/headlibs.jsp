<%@include file="/apps/migration/global.jsp"%>

<cq:includeClientLib categories="cq.jquery,personalization,cq.foundation-main,
        cq.wcm.edit,cq.tagging,cq.security"/>
<%
    currentDesign.writeCssIncludes(pageContext); 
%>
<cq:include script="init.jsp"/>