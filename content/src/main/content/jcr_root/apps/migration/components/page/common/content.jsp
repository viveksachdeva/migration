<%@include file="/apps/migration/global.jsp"%>
<c:set var="locale">_${requestScope.language}</c:set>
<div class="contents">
	<div class="c_left">
        <cq:include script="contentleft${locale}.jsp" />
    </div>
    <div class="c_mid">
        <cq:include script="contentcenter.jsp" />
    </div>
    <div class="c_right">
        <cq:include script="contentright.jsp" />
    </div>
    <div class="clr"></div>
</div>

