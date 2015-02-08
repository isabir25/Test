
<html>
	<head>
        <meta name="layout" content="application"/>		
		<g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>    
	</head>
    <body>
    	<h1><g:message code="default.create.label" args="[entityName]" /></h1>
    	
    	<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			
    	<g:hasErrors bean="${tag}">
		    <g:eachError><p><g:message error=""/></p></g:eachError>
		</g:hasErrors>

		<g:form method="post" >
			
			<div>
				<label for="Tag">
					<g:message code="tag.label" default="Tag" />
					<span class="">*</span>
				</label>
				<g:textField name="name" value="${tag?.name}"/>
			</div>
			<div>
				<label for="Description">
					<g:message code="description.label" default="Description" />
					<span class="">*</span>
				</label>
				<g:textField name="description" value="${tag?.description}"/>
			</div>
			
			<fieldset class="buttons">
				<g:actionSubmit class="save" action="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />				
			</fieldset>
		</g:form>
	</body>
</html>