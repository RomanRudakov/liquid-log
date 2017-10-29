<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>SD40 Performance indicator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<script src="/js/jquery-3.1.1.min.js"></script>
	
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
          integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js"
        integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"></script>
    <link rel="stylesheet" href="/css/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css"/>
    
</head>


<body>

<div class = "container">

<div>
<a class="btn btn-danger" href='/'>Client list</a>
</div>

<form action = "/parserLog/res">
<div>
	<label><input type = "text" name = "nameDB"> Name DataBase</label>
</div>

<div>
<label><select name = "typePars">
  <option>sdng</option>
  <option>gc</option>
  <option>top</option></select> Mode parsing</label>
</div>

<div>
<label><input type = "text" name = "pathLog"> Path to log</label>
</div>

<div>
<label><input type = "text" name = "timeZona"> Time</label>
</div>

<div>
<label><input type = "checkbox" name = "trace"> Trace result</label>
</div>

<button type="submit" class="btn btn-success" >PARSING!!!</button>

</div>
</form>

</body>
</html>