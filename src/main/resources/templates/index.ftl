<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

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
		button {
			background-color: #04AA6D;
			color: white;
			padding: 14px 20px;
			border: none;
			cursor: pointer;
			width: 30%;
		}
	</style>
	<body>
		<!-- Navbar (sit on top) -->
		<div class="w3-top">
			<div class="w3-bar w3-white w3-padding w3-card" style="letter-spacing:4px;">
			<a href=<@spring.url '/'/> class="w3-bar-item w3-button">ShortURL</a>
			
			<div class="w3-right w3-hide-small">
				<@security.authorize access="!isAuthenticated()">
	    			<a href=<@spring.url '/login'/> class="w3-bar-item w3-button">Log in</a>
					<a href=<@spring.url '/signup'/> class="w3-bar-item w3-button">Sign up</a>
				</@security.authorize>
				
				<@security.authorize access="isAuthenticated()">
					<a href=<@spring.url '/menu'/> class="w3-bar-item w3-button">URL Statistics</a>
    				<a href=<@spring.url '/logout'/> class="w3-bar-item w3-button">Log out</a>
				</@security.authorize>
			</div>
		</div>
		
		
		<!-- Page content -->
		<div class="w3-display-middle1">
			<div class="w3-center">
				<h2>Create short URL</h2>
				<form name="createShortURL" action="" method="POST">
					<@spring.formInput "shortURL.longURL" 'placeholder="https://www.example.com/html/html_examples.html" size="75" style="margin-bottom:5px"' "text" /><br>
					<input type="submit" value="Create" class="button">
				</form>
				
				<#if errorMessage??>
					<div style="color:red;font-style:italic;">
						<h1>${errorMessage}</h1>
					</div>
				</#if>
				<#if message??>
					<div style="color:black;font-style:italic;">
						<h1>${message}</h1>
					</div>
				</#if>
			</div>
		</div>
		
		<!-- Footer -->
		<footer class="w3-bottom w3-center w3-light-grey w3-padding-16">
			<span>Copyright &copy;ShortURL 2022</span>
		</footer>
	</body>
</html>
