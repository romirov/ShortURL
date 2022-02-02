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

		/* Full-width input fields */
		input[type=text], input[type=password] {
			width: 100%;
			padding: 15px;
			margin: 5px 0 22px 0;
			display: inline-block;
			border: 1px solid #ccc;
			background: #f1f1f1;
		}

		input[type=text]:focus, input[type=password]:focus {
			background-color: #ddd;
			outline: none;
		}

		hr {
			border: 1px solid #f1f1f1;
			margin-bottom: 25px;
		}

		/* Set a style for all buttons */
		button {
			background-color: #04AA6D;
			color: white;
			padding: 14px 20px;
			border: none;
			cursor: pointer;
			width: 30%;
		}

		button:hover {
			opacity:1;
		}

		/* Extra styles for the cancel button */
		.cancelbtn {
			padding: 14px 20px;
			background-color: #f44336;
		}

		/* Add padding to container elements */
		.container {
			padding: 16px;
		}

		/* Clear floats */
		.clearfix::after {
			content: "";
			clear: both;
			display: table;
		}
	</style>
	<body>
		<!-- Navbar (sit on top) -->
		<div class="w3-top">
			<div class="w3-bar w3-white w3-padding w3-card" style="letter-spacing:4px;">
			<a href=<@spring.url '/'/> class="w3-bar-item w3-button">ShortURL</a>
		</div>
		
		<!-- Page content -->
		<div class="w3-display-middle2">
			<div class="w3-center">
				<form name="userSignupForm" action="" method="POST">
					<div class="container">
						<h1>Sign Up</h1>
						<p>Please fill in this form to create an account.</p>
						<hr>

						<h3>Username</h3>
						<@spring.formInput "userSignupForm.username" 'placeholder="Enter Username" size="75" style="margin-bottom:5px" required' "text"/><br>
						
						<h3>Password</h3>
						<@spring.formPasswordInput "userSignupForm.password" 'placeholder="Enter Password" size="75" style="margin-bottom:5px" required'/><br>
					
						<h3>Repeat Password</h3>
						<@spring.formPasswordInput "userSignupForm.passwordConfirm" 'placeholder="Repeat Password" size="75" style="margin-bottom:5px" required'/><br>
    
						<div class="clearfix">
							<button type="submit" class="signupbtn">Sign Up</button>
							<button type="button" class="cancelbtn">Cancel</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<!-- Footer -->
		<footer class="w3-bottom w3-center w3-light-grey w3-padding-16">
			<span>Copyright &copy;ShortURL 2022</span>
		</footer>
	</body>
</html>
