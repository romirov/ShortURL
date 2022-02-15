<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
	<title>ShortURL</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<style>
		<#include "w3.css">
		body {
			font-family: "Times New Roman", Georgia, Serif;
		}
		
		h1, h2, h3, h4, h5, h6 {
			font-family: "Playfair Display";
			letter-spacing: 5px;
		}
		
		form {
  			border: 3px solid #ccc;
		}
		
		hr {
			border: 1px solid #f1f1f1;
			margin-bottom: 25px;
		}

		.updatebtn {
			background-color: #04AA6D;
			color: white;
			border: none;
			cursor: pointer;
			width: 150px;
		}

		
		.deletebtn {
			background-color: #f44336;
			border: none;
			cursor: pointer;
			width: 100%;
		}
		
		table, td, th {
			border: 1px solid black;
		}

		.table {
			border-collapse: collapse;
			width: 100%;
		}

		.th {
			height: 70px;
		}
		
		.scroll-table-body {
			height: 700px;
			overflow-x: auto;
			margin-top: 0px;
			margin-bottom: 20px;
			border-bottom: 1px solid #eee;
		}
	
		.scroll-table table {
			width: 100%;
			table-layout: fixed;
			border: none;
		}

		.scroll-table thead th {
			font-weight: bold;
			text-align: left;
			border: none;
			padding: 10px 15px;
			background: #d8d8d8;
			font-size: 14px;
			border-left: 1px solid #ddd;
			border-right: 1px solid #ddd;
		}
		
		.scroll-table tbody td {
			text-align: left;
			border-left: 1px solid #ddd;
			border-right: 1px solid #ddd;
			padding: 10px 15px;
			font-size: 14px;
			vertical-align: top;
		}
	
		.scroll-table tbody tr:nth-child(even){
			background: #f3f3f3;
		}
 
		::-webkit-scrollbar {
			width: 6px;
		} 
	
		::-webkit-scrollbar-track {
			box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
		} 
		
		::-webkit-scrollbar-thumb {
			box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
		}
	</style>
	<body>
		<div class="w3-top">
			<div class="w3-bar w3-white w3-padding w3-card" style="letter-spacing:4px;">
			<a href=<@spring.url '/'/> class="w3-bar-item w3-button">ShortURL</a>
		
			<div class="w3-right w3-hide-small">
				<a href=<@spring.url '/logout'/> class="w3-bar-item w3-button">Log out</a>
			</div>
		</div>
		
		<!-- Page content -->
		<div class="w3-center">
			<h2>URLs</h2>
			<div class="scroll-table">
				<table class="w3-table-all" >
					<thead>
						<th>ID</th>
						<th>Short URL</th>
						<th>Long URL</th>
						<th>Transition Counter</th>
						<th>Current Lifetime</th>
						<th>Update Lifetime</th>
						<th>Delete</th>
					</thead>
				</table>
				<div class="scroll-table-body">
					<table>
						<tbody>
							<#if shortURLs??>
								<#list shortURLs as shortURL>
									<tr>
										<td class="td">${shortURL.id}</td>
										<td class="td">${shortURL.shortURL}</td>
										<td class="td">${shortURL.longURL}</td>
										<td class="td">${shortURL.transitionCounter}</td>
										<td class="td">
											<#setting datetime_format= "dd.MM.yyyy"/>			
											<p size="30">${shortURL.lifetime?datetime}</p>
										</td>
										<td class="td">
											<form id="update" action="/menu/update/${shortURL.id}" method="POST">
												<input type="date" name="lifetime" value="${lifetime?date}">
												<input type="submit" class="updatebtn" value="Update">
											</form>
										</td>
										<td class="td">
											<form name="delete" action="/menu/delete/${shortURL.id}" method="POST">
												<input type="submit" class="deletebtn" value="Delete">
											</form>
										</td>
									</tr>
								</#list>
							</#if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<footer class="w3-bottom w3-center w3-light-grey w3-padding-16">
			<span>Copyright &copy;ShortURL 2022</span>
		</footer>
	</body>
</html>
