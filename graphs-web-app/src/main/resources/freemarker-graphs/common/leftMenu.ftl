<#macro projectListNavbar projectList={}>
<div id="left-menu">
	<ul class="nav nav-tabs nav-stacked span3">
		<li>
			<a href="${rc.getContextPath()}/addProject">Add Project</a>
			<#list projectList as project>
			<a href="${rc.getContextPath()}/projects/${project}">${project}</a>	
			</#list>
		</li>
	</ul>
</div>
</#macro>