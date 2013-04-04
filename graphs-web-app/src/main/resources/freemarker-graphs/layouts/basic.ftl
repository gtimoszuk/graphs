<#macro basic title="none">

<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/common/prolog.ftl">
		<title>
			Graphs
		</title>
	</head>

	<body>
		<div class="container-fluid">
			<div class="row-fluid">
				<h1> header </h1>
			</div>
			<div class="row-fluid">
			<#nested>
			</div>
			<div class="row-fluid">
				<h2> footer </h2>
			</div>
		</div>
	</body>
</html>

</#macro>
