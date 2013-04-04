<@layout.basic>

<#import "/common/leftMenu.ftl" as leftMenu	>
<@leftMenu.projectListNavbar projectList=projects>
</@leftMenu.projectListNavbar>

<div id="addProjectForm" class="span9">
    <div class="row-fluid">
		<form method="post" action="${rc.contextPath}/createProject" modelAttribute="createProjectForm" id="createProjectForm">
			<fieldset>
				<div class="row-fluid">
					<div class="span12"><h1> Add Project </h1></div>
				</div>
				<div class="row-fluid">
					<div class="span3">ProjectName</div>
					<div class="span6"> <input type="text" name="projectName" placeholder="Type something…"> </div>
				</div>
				<div class="row-fluid">
					<div class="span3">Repo URL</div>
					<div class="span6"> <input type="text" name="repoUrl" placeholder="Type something…"> </div>
				</div> 
				<div class="row-fluid">
					<div class="span3">Repo type</div>
					<div class="span6">
						<select name="repoType">
							<#list revisionControlSystems?keys as revisionControlSystemKey>
								<option value="${revisionControlSystemKey}">${revisionControlSystems[revisionControlSystemKey]}</option>
							</#list>
						</select>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span3">Build system</div>
					<div class="span6">
						<select name="buildSystem">
							<#list buildTools?keys as buildToolKey>
								<option value="${buildToolKey}">${buildTools[buildToolKey]}</option>
							</#list>
						</select>
					</div>
				</div>
			</fieldset>
			<div class="row-fluid">
				 <button type="submit" class="btn btn-large btn-primary ">Submit</button>
			</div>
		</form>
	</div>
</div>

</@layout.basic>