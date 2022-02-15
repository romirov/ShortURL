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
		
		form {
  			border: 3px solid #ccc;
		}

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

		button {
			background-color: #04AA6D;
			color: white;
			padding: 14px 20px;
			border: none;
			cursor: pointer;
			width: 30%;
		}

		button:hover {
			opacity: 0.8;
		}

		.cancelbtn {
			background-color: #f44336;
			padding: 14px 20px;
			border: none;
			cursor: pointer;
			width: 30%;
		}

		.container {
			padding: 16px;
		}

		span.psw {
			float: right;
			padding-top: 16px;
		}
  
  		.cancelbtn {
			width: 30%;
		}
	</style>
	<body>
		<div class="w3-top">
			<div class="w3-bar w3-white w3-padding w3-card" style="letter-spacing:4px;">
			<a href=<@spring.url '/'/> class="w3-bar-item w3-button">ShortURL</a>
			<div class="w3-right w3-hide-small">
				<a href=<@spring.url '/signup'/> class="w3-bar-item w3-button">Sign up</a>
			</div>
		</div>
		
		<div class="w3-display-middle1">
			<div class="w3-center">
				<form name="userLogIn" action="" method="POST">
					<div class="container">
						<h1>Login</h1>
						<p>Please fill in this form to log in.</p>
						<hr>
						
						<h3>Username</h3>
						<@spring.formInput "userLogin.username" 'placeholder="Enter Username" size="75" style="margin-bottom:5px" required' "text"/><br>
						
						<h3>Password</h3>
						<@spring.formPasswordInput "userLogin.password" 'placeholder="Enter Password" size="75" style="margin-bottom:5px" required'/><br>
					
		                
						<button type="submit" class="btn">Login</button>
						<button type="cancelbtn" class="cancelbtn">Cancel</button>
					</div>
				</form>
			</div>
		</div>
		
		<footer class="w3-bottom w3-center w3-light-grey w3-padding-16">
			<span>Copyright &copy;ShortURL 2022</span>
		</footer>
	</body>
</html>
