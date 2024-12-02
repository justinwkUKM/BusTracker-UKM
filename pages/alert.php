<!DOCTYPE html>
<html>
  <head>
    <title>alert</title>
    <!-- Bootstrap -->
    <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../../../vendor/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="../../dist/assets/styles.css" rel="stylesheet" media="screen">
     <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
      <script>
        function goBack() {
            window.history.back();
        }
      </script>
  </head>
  <body id="alert">
    <div class="container">

      <form class="form-alert" method="post">
        <h2 class="form-alert-heading">We Are Sorry!</h2><hr />
            <div class='alert alert-info'>
            you are not allowed to make changes on this page
            </div>  
        <a class="btn btn-primary" onclick="goBack()" style="float: right;">back</a>
      </form>

    </div> <!-- /container -->
    <script src="../../vendor/jquery/jquery-1.9.1.min.js"></script>
    <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>