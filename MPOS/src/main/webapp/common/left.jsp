<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
    <!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<div class="page-sidebar navbar-collapse collapse">
		<!-- BEGIN SIDEBAR MENU -->        
		<ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200">
			<!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
			<li class="sidebar-toggler-wrapper">
				<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				<div class="sidebar-toggler">
				</div>
				<!-- END SIDEBAR TOGGLER BUTTON -->
			</li>
			<!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
			<li class="sidebar-search-wrapper">&nbsp;</li>
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
        <c:set var="cureReqUri" value="${pageContext.request.requestURI}"/>							
		<c:forEach var="menu" items="${menus}" varStatus="status">
			<c:if test="${menu.value==null||menu.value.size()==0}">
				<c:if test="${menu.key.uri.startsWith('/')}">
					<c:set var="menuUri" value="${contextPath}${menu.key.uri}/"/>
				</c:if>
				<c:if test="${!menu.key.uri.startsWith('/')}">
					<c:set var="menuUri" value="${contextPath}/${menu.key.uri}/"/>
				</c:if>
				<c:if test="${cureReqUri.startsWith(menuUri)}">						
						<c:set var="selectedNode" value="${menu.key}"/>			
				</c:if>	
			</c:if>
			<c:if test="${menu.value!=null&&menu.value.size()>0}">		
				<c:forEach var="tempmenu" items="${menu.value}">
					<c:set var="tempMenuUri" value="${tempmenu.uri}"/>
					<c:if test="${tempMenuUri.startsWith('/')}">
						<c:set var="reqUri" value="${contextPath}${tempMenuUri}/"/>
					</c:if>
					<c:if test="${!tempMenuUri.startsWith('/')}">
						<c:set var="reqUri" value="${contextPath}/${tempMenuUri}/"/>
					</c:if>				
					<c:if test="${cureReqUri.startsWith(reqUri)}">					
						<c:set var="selectedNode" value="${tempmenu}"/>			
					</c:if>
				</c:forEach>
			</c:if>		
			<c:set var="classStr" value=""/>						
			<c:if test="${status.first}">			
				<c:if test="${selectedNode!=null&&selectedNode.nodeId==menu.key.nodeId}">		
					<c:set var="classStr" value="start active"/>									
				</c:if>
				<c:if test="${selectedNode!=null&&selectedNode.nodeId!=menu.key.nodeId&&selectedNode.pid==menu.key.nodeId}">		
					<c:set var="classStr" value="start active open"/>			
				</c:if>
				<c:if test="${selectedNode.nodeId!=menu.key.nodeId&&selectedNode.pid!=menu.key.nodeId}">		
					<c:set var="classStr" value="start"/>		
				</c:if>
			</c:if>
			<c:if test="${!status.first}">		
				<c:if test="${selectedNode!=null&&selectedNode.nodeId==menu.key.nodeId}">
					<c:set var="classStr" value="active"/>					
				</c:if>
				<c:if test="${selectedNode!=null&&selectedNode.nodeId!=menu.key.nodeId&&selectedNode.pid==menu.key.nodeId}">		
					<c:set var="classStr" value="active open"/>			
				</c:if>
				<c:if test="${selectedNode!=null&&selectedNode.nodeId!=menu.key.nodeId&&selectedNode.pid!=menu.key.nodeId}">		
					<c:set var="classStr" value=""/>			
				</c:if>
			</c:if>				
						
			<jsp:useBean id="menuBean" class="com.mpos.dto.TadminNodes"></jsp:useBean>	
			<jsp:setProperty name="menuBean" property="bitFlag" value="${menu.key.bitFlag}"></jsp:setProperty>	
			<%
			boolean isAdmin=((com.mpos.dto.TadminUser)session.getAttribute("Logined")).getAdminRole().getRoleId()==1;
			Long userRights=null;
			if(session.getAttribute("rights")!=null){
				userRights=(Long)session.getAttribute("rights");
			}			
			if(isAdmin||(userRights&menuBean.getBitFlag())>0){				
			%>	
			<li class="${classStr}">
				<c:choose>
					<c:when test="${menu.key.uri eq '/home'}">
						<c:set var="menuIcon" value="icon-home"/>						
					</c:when>
					<c:when test="${menu.key.uri eq '/rights'}">
						<c:set var="menuIcon" value="icon-wrench"/>
					</c:when>
					<c:when test="${menu.key.uri eq '/settings'}">
						<c:set var="menuIcon" value="icon-settings"/>
					</c:when>
					<c:when test="${menu.key.uri eq '/managerlog'}">
						<c:set var="menuIcon" value="icon-feed"/>
					</c:when>
					<c:when test="${menu.key.uri eq '/point'}">
						<c:set var="menuIcon" value="icon-note"/>
					</c:when>
					<c:when test="${menu.key.uri eq '/interface'}">
						<c:set var="menuIcon" value="icon-calendar"/>
					</c:when>					
					<c:otherwise>
						<c:set var="menuIcon" value="icon-home"/>
					</c:otherwise>
				</c:choose>
				
			
				<c:set var="menuUri" value="${pageContext.request.contextPath}${menu.key.uri}"/>
				<c:set var="hasSubMenu" value="${menu.value!=null&&menu.value.size()>0}"/>
				<a href="${hasSubMenu?'javascript;':menuUri}">
				<i class="<c:out value="${menuIcon}"/>"></i> 
				<span class="title"><c:out value="${menu.key.name}"/></span>
				<c:if test="${selectedNode!=null&&selectedNode.nodeId==menu.key.nodeId}">				
					<span class="selected"></span>								
				</c:if>	
				<c:if test="${hasSubMenu}">
					<c:if test="${selectedNode==null}">
						<span class="arrow"></span>
					</c:if>
					<c:if test="${selectedNode!=null&&selectedNode.pid!=0&&selectedNode.pid==menu.key.nodeId}">
						<span class="arrow open"></span>
					</c:if>						
				</c:if>	
				</a>
				<c:if test="${menu.value!=null&&menu.value.size()>0}">
					<ul class="sub-menu">
					<c:forEach var="subMenu" items="${menu.value}" varStatus="substatus">						
						<jsp:useBean id="subMenuBean" class="com.mpos.dto.TadminNodes"></jsp:useBean>	
						<jsp:setProperty name="subMenuBean" property="bitFlag" value="${subMenu.bitFlag}"></jsp:setProperty>
						<%if(isAdmin||(userRights&subMenuBean.getBitFlag())>0){%>
					   <li class="${(selectedNode!=null&&subMenu.nodeId==selectedNode.nodeId)?'active':''}">
							<a href="${contextPath}${subMenu.uri}">
							${subMenu.name}
							</a>
						</li>
						<%} %>
					</c:forEach>											
					</ul>
				</c:if>	
			</li>
			<%} %>			    
		</c:forEach>									
			
		</ul>
		<!-- END SIDEBAR MENU -->
	   </div>
    </div>    
   <!-- END SIDEBAR -->