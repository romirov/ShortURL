<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
	<title>ShortURL</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<style>
		<#include "w3.css">
		body {font-family: "Times New Roman", Georgia, Serif;}
		h1, h2, h3, h4, h5, h6 {
			font-family: "Playfair Display";
			letter-spacing: 5px;
		}
		
		/* Bordered form */
		form {
  			border: 3px solid #ccc;
		}

		/* Full-width inputs */
		input[type=text], input[type=password] {
			width: 100%;
			padding: 12px 20px;
			margin: 8px 0;
			display: inline-block;
			border: 1px solid #ccc;
			box-sizing: border-box;
			background: #f1f1f1;
		}
		
		hr {
			border: 1px solid #f1f1f1;
			margin-bottom: 25px;
		}

		/* Set a style for all buttons */
		submitbtn {
			background-color: #04AA6D;
			color: white;
			padding: 14px 20px;
			border: none;
			cursor: pointer;
			width: 30%;
		}

		/* Add a hover effect for buttons */
		submitbtn:hover {
			opacity: 0.8;
		}

		/* Extra style for the cancel button (red) */
		.cancelbtn {
			background-color: #f44336;
			padding: 14px 20px;
			border: none;
			cursor: pointer;
			width: 30%;
		}
		
		table, td, th {
			border: 1px solid black;
		}

		table {
			border-collapse: collapse;
			width: 100%;
		}

		th {
			height: 70px;
		}
	</style>
	<body>
		<!-- Navbar (sit on top) -->
		<div class="w3-top">
			<div class="w3-bar w3-white w3-padding w3-card" style="letter-spacing:4px;">
			<a href=<@spring.url '/'/> class="w3-bar-item w3-button">ShortURL</a>
			<!-- Right-sided navbar links-->
			<div class="w3-right w3-hide-small">
				<a href=<@spring.url '/logout'/> class="w3-bar-item w3-button">Log out</a>
			</div>
		</div>
		
		<!-- Page content -->
		<div class="w3-display-middle1">
			<div class="w3-center">
				<h2>URLs</h2>				
				<form name="updateShortURL" action="" method="POST">
					<table class="w3-table-all" width="100%">
						<thead>
							<th class="th" width="100">ID</th>
							<th class="th" width="200">Short URL</th>
							<th class="th" width="400">Long URL</th>
							<th class="th" width="200">Transition Counter</th>
							<th class="th" width="200">Lifetime</th>
						</thead>
						<#list user.shortURLs as shortURL>
							<tr>
								<td class="td">${shortURL.id}</td>
								<td class="td">${shortURL.shortURL}</td>
								<td class="td">${shortURL.longURL}</td>
								<td class="td">${shortURL.transitionCounter}</td>
								<td class="td"><@spring.formInput "shortURL.lifetime" 'placeholder="${shortURL.lifetime}" size="15" style="margin-bottom:5px"' "text" /></td>
								<td class="td">
									<button type="submitbtn" class="submitbtn">Submit</button>
									<button type="cancelbtn" class="cancelbtn">Cancel</button>
								</td>
							</tr>
						</#list>
					</table>
				</form>
			</div>
		</div>
		
		<!-- Footer -->
		<footer class="w3-bottom w3-center w3-light-grey w3-padding-16">
			<span>Copyright &copy;ShortURL 2022</span>
		</footer>
	</body>
</html>
