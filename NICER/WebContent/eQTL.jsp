<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<title>eQTL</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link href="https://fonts.googleapis.com/css?family=Rubik:300,400,500"
	rel="stylesheet">
<link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/owl.theme.default.min.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/aos.css">
<link rel="stylesheet" href="css/ionicons.min.css">
<link rel="stylesheet" href="css/bootstrap-datepicker.css">
<link rel="stylesheet" href="css/jquery.timepicker.css">
<link rel="stylesheet" href="css/flaticon.css">
<link rel="stylesheet" href="css/icomoon.css">
<link rel="stylesheet" href="css/style.css">
</head>
<script langugage='javascript'>
	function on_submit() {
		var gsWin = window.open('about:blank', 'payviewer',
				'width=1200, height=400');
		var frm = document.eeqq;
		frm.action = 'eQTL';
		frm.target = "payviewer";
		frm.method = "post";
		frm.enctype = "multipart/form-data";
		frm.submit();
	}
</script>
<body>
<%-- 	<script>
		if (<%=session.getAttribute("loginId")%> == null) {
			alert("You have to log in to use that.");
			document.location.href = "loginForm.jsp";
		}
	</script> --%>

	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar" data-aos="fade-down" data-aos-delay="500">
		<div class="container">
			<a class="navbar-brand" href="index.html">CBLAB - Computational
				Bioinformatics LABoratory</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>

			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a href="index.html"
						class="nav-link">Home</a></li>
					<li class="nav-item"><a href="GAMMA_index.jsp"
						class="nav-link">GAMMA</a></li>
					<li class="nav-item"><a href="MultiTrans_index.jsp"
						class="nav-link">MultiTrans</a></li>
					<li class="nav-item"><a href="NICE_index.jsp" class="nav-link">NICE</a></li>
					<li class="nav-item"><a href="eQTL_index.jsp" class="nav-link">eQTL</a></li>
					<li class="nav-item"><a href="Manhattan_index.jsp"
						class="nav-link">Manhattan</a></li>
					<li class="nav-item"><a href="Signup.html" class="nav-link">SignUp</a></li>
					<li class="nav-item"><a href="loginForm.jsp" class="nav-link">SignIn</a></li>
					<li class="nav-item"><a href="logoutProc.jsp" class="nav-link">SignOut</a></li>
				</ul>
			</div>
		</div>
	</nav>


	<section class="ftco-cover"
		style="background-image: url(images/genes_650x400_61470411347.jpg); max-width: auto; max-height: auto;"
		id="section-home" data-aos="fade" data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row align-items-center ftco-vh-75">
				<div class="col-md-7">
					<h1 class="ftco-heading mb-3" data-aos="fade-up"
						data-aos-delay="500">eQTL</h1>
					<h2 class="h5 ftco-subheading mb-5" data-aos="fade-up"
						data-aos-delay="600">
						<a href="http://genetics.cs.ucla.edu/multiTrans/" target="_blank">eQTL</a>
						is a cis/trans plot for GWAS
					</h2>
				</div>
			</div>
		</div>
	</section>


	<br>
	<br>
	<div class="container">
		<div class="col-md-7">
			<h1 data-aos="fade-up" data-aos-delay="500">Upload files to use
				eQTL</h1>
			<!--  class="ftco-heading mb-3"  -->
			<h2 data-aos="fade-up" data-aos-delay="600">For analysis using
				eQTL, you need to upload SNP_position file, phenotype_position file
				and p-value file.</h2>
			<!-- class="h5 ftco-subheading mb-5" -->
		</div>

		<footer class="ftco-footer ftco-bg-dark ftco-section">
			<div class="ftco-section contact-section">
				<div class="container">
					<div class="row block-9">
						<div class="col-md-6 pr-md-5">
							<form name="eeqq" action="eQTL" method="post"
								enctype="multipart/form-data">
								<div class="form-group">
									<input type="email" class="form-control" name="emailAddress"
										placeholder="The email address to receive the results.">
									<table cellspacing="100%">
										<tr>
											<th cellspacing="50%"><input type="text"
												placeholder=" Upload SNP_pos file" disabled></th>
											<th><input type="file" name="SNP_pos"></th>
										</tr>
									</table>

									<table cellspacing="100%">
										<tr>
											<th cellspacing="50%"><input type="text"
												placeholder=" Upload Phenotype_pos file" disabled></th>
											<th><input type="file" name="Phenotype_pos"></th>
										</tr>
									</table>
									<table cellspacing="100%">
										<tr>
											<th cellspacing="50%"><input type="text"
												placeholder=" Upload P-Value file" disabled></th>
											<th><input type="file" name="P-Value"></th>
										</tr>
									</table>

								</div>
							</form>
							<input type="button" name="upload" value="Submit"
								class="btn btn-primary py-3 px-5" onclick='on_submit()'>
						</div>
						<div class="col-md-6"
							style="background-image: url(images/word_cloud.png); width: 560px; height: 448px;">
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-md-12 text-center">
						<p></p>
					</div>
				</div>
		</footer>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-migrate-3.0.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.easing.1.3.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.stellar.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/aos.js"></script>
	<script src="js/jquery.animateNumber.min.js"></script>
	<script src="js/google-map.js"></script>
	<script src="js/main.js"></script>

</body>
</html>