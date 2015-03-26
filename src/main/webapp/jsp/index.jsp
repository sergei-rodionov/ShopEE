<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<div class="bodyShop">
    <div class="bodyMenu">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="${pageContext.request.contextPath}/admin" class="pMenuAdmin">Admin page</a>
        </sec:authorize>
        <p class="pMenuCategory">Categories <a href="#" onclick="getProductsByCategory('all')" class="linkCategoryAll">(see all)</a></p>
        <ul class="ulMenu" id="ulCategory">
            <!-- category or menu list -->
        </ul>
    </div>
    <div class="bodyMain">
        <div class="bodyHead" id="bodyProductsHead">All</div>
        <div class="divBodyList" id="bodyProductsList">
            <!-- main data list -->
        </div>
    </div>
</div>


<%@ include file="bottom.jsp" %>