<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>NICE</title>
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
<link rel="stylesheet" href="css/tab.css">


</head>
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
					<li class="nav-item"><a href="NICE.jsp" class="nav-link">NICER</a></li>
					<li class="nav-item"><a href="eQTL.html" class="nav-link">eQTL</a></li>
					<li class="nav-item"><a href="Manhattan.html" class="nav-link">Manhattan</a></li>
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
						data-aos-delay="500">NICER</h1>
					<h2 class="h5 ftco-subheading mb-5" data-aos="fade-up"
						data-aos-delay="600">
						Next-generation Intersample Correlation Emended Renew<a
							href="http://genetics.cs.ucla.edu/nice_jemdoc/" target="_blank">(NICER)</a>
						is a statistical test for correcting for expression heterogeneity
						inherent in expression dataset due to confounding from unmodeled
						factors. NICER estimates inter-sample correlation structure using
						only the genes with confounding effects and incorporates it as
						signatures of the systematic confounding effects to correct for
						it. (Click the bold text if you want to use the program manually.)
					</h2>

				</div>
			</div>
		</div>
	</section>


	<br>
	<br>
	<div class="container">
		<div class="col-md-7">
			<h1 data-aos="fade-up" data-aos-delay="500">Upload Input files
				for NICER</h1>
			<h2 data-aos="fade-up" data-aos-delay="600">You need a SNP file
				and phenotype file .</h2>

		</div>
		<div class="form-group" align="right">
			<input type="button" name="Server1" value="Server1"
				class="btn btn-primary py-3 px-5"
				onclick="window.location.href='http://cblab.dongguk.edu:8080/NICER/NICE.jsp'">
			<input type="button" name="GCE1" value="GCE1"
				class="btn btn-primary py-3 px-5"
				onclick="window.location.href ='http://104.196.196.160:8080/NICER/NICE.jsp'">
		</div>

		<footer class="ftco-footer ftco-bg-dark ftco-section">
			<div class="ftco-section contact-section">
				<div class="container">
					<div class="row block-9">
						<div class="col-md-6 pr-md-5">
							<input id="tab1" type="radio" name="tabs" value="type1"
								checked="checked" style="display: none"> <label
								for="tab1" data-tab="tab-1" style="padding: 15px 18px">PLINK
								DATA</label> <input id="tab4" type="radio" name="tabs" value="type4"
								style="display: none"> <label for="tab4"
								data-tab="tab-4" style="padding: 15px 18px">VCF DATA</label> <input
								id="tab2" type="radio" name="tabs" value="type2"
								style="display: none"> <label for="tab2"
								data-tab="tab-2" style="padding: 15px 18px">DATA1</label> <input
								id="tab3" type="radio" name="tabs" value="type3"
								style="display: none"> <label for="tab3"
								data-tab="tab-3" style="padding: 15px 18px">DATA2</label><br>

							<div id="tab-1" class="tab-content current">
								<form action="NICEServlet" method="post"
									enctype="multipart/form-data">

									<input type="radio" name="set_num" checked="checked"
										value="5set" /> 5set <input type="radio" name="set_num"
										value="10set" /> 10set

									<div class="form-group">
										<input type="email" class="form-control" name="emailAddress"
											placeholder="The email address to receive the results.">
									</div>
									<input type="text" name="tabss" value="type1"
										style="display: none">
									<div class="form-group">
										<table cellspacing="100%">
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload BIM file" disabled></th>
												<th><input type="file" name="BIM_file"></th>
											</tr>
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload BED file" disabled></th>
												<th><input type="file" name="BED_file"></th>
											</tr>
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload FAM file" disabled></th>
												<th><input type="file" name="FAM_file"></th>
											</tr>
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload Expression file" disabled></th>
												<th><input type="file" name="EX_file"></th>
											</tr>
										</table>
									</div>
									<div class="form-group">
										<input type="submit" name="upload" value="Submit"
											class="btn btn-primary py-3 px-5">
									</div>
								</form>

							</div>
							<div id="tab-2" class="tab-content">
								<form action="NICEServlet" method="post"
									enctype="multipart/form-data">

									<input type="radio" name="set_num" checked="checked"
										value="5set" /> 5set <input type="radio" name="set_num"
										value="10set" /> 10set

									<div class="form-group">
										<input type="email" class="form-control" name="emailAddress"
											placeholder="The email address to receive the results.">
									</div>
									<input type="text" name="tabss" value="type2"
										style="display: none">

									<div class="form-group">
										<table cellspacing="100%">
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload SNP file" disabled></th>
												<th><input type="file" name="SNPfile"></th>
											</tr>
										</table>
									</div>
									<div class="form-group">
										<table cellspacing="100%">
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload Phenotype file" disabled></th>
												<th><input type="file" name="Phenotypefile"></th>
											</tr>
										</table>
									</div>

									<div class="form-group">
										<input type="submit" name="upload" value="Submit"
											class="btn btn-primary py-3 px-5">
									</div>
								</form>
							</div>
							<div id="tab-3" class="tab-content">
								<form action="NICEServlet" method="post"
									enctype="multipart/form-data">

									<input type="radio" name="set_num" checked="checked"
										value="5set" /> 5set <input type="radio" name="set_num"
										value="10set" /> 10set

									<div class="form-group">
										<input type="email" class="form-control" name="emailAddress"
											placeholder="The email address to receive the results.">
									</div>
									<input type="text" name="tabss" value="type3"
										style="display: none">

									<div class="form-group">
										<table cellspacing="100%">
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload rightdim_SNP file" disabled></th>
												<th><input type="file" name="SNPfile2"></th>
											</tr>
										</table>
									</div>
									<div class="form-group">
										<table cellspacing="100%">
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload rightdim_Phenotype file" disabled></th>
												<th><input type="file" name="Phenotypefile2"></th>
											</tr>
										</table>
									</div>

									<div class="form-group">
										<input type="submit" name="upload" value="Submit"
											class="btn btn-primary py-3 px-5">
									</div>
								</form>
							</div>
							<div id="tab-4" class="tab-content">
								<form action="NICEServlet" method="post"
									enctype="multipart/form-data">

									<input type="radio" name="set_num" checked="checked"
										value="5set" /> 5set <input type="radio" name="set_num"
										value="10set" /> 10set

									<div class="form-group">
										<input type="email" class="form-control" name="emailAddress"
											placeholder="The email address to receive the results.">
									</div>
									<input type="text" name="tabss" value="type4"
										style="display: none">

									<div class="form-group">
										<table cellspacing="100%">
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload VCF file" disabled></th>
												<th><input type="file" name="VCFfile"></th>
											</tr>
											<tr>
												<th cellspacing="50%"><input type="text"
													placeholder=" Upload Expression file" disabled></th>
												<th><input type="file" name="Expfile"></th>
											</tr>
										</table>
									</div>

									<div class="form-group">
										<input type="submit" name="upload" value="Submit"
											class="btn btn-primary py-3 px-5">
									</div>
								</form>
							</div>
						</div>

						<div class="col-md-6" align="right"
							style="background-image: url(images/word_cloud.png); width: 330px; height: 340px;">
							<input type="button" name="Documentation" value="Documentation"
								class="btn btn-primary py-3 px-5"
								onclick="window.location.href ='http://github.com/JuhunC/NICER'">
						</div>
						<div align="right">
							<a
								href="http://cblab.dongguk.edu:8080/NICER/Download?file=*home*joohun484*Desktop*NICER*sample_dataset.zip"
								target="_blank">Download Sample Dataset</a>
						</div>
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
	<script>
		//tab-menu
		$(document).ready(function() {
			$('label').click(function() {
				var tab_id = $(this).attr('data-tab');
				$('.tab-content').removeClass('current');
				$(this).addClass('current');
				$("#" + tab_id).addClass('current');
			})
		})
	</script>
</body>
</html>