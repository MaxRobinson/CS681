<%-- 
    Document   : index
    Created on : Aug 5, 2018, 3:55:52 PM
    Author     : max
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <!-- Link to site: https://web7.jhuep.com/mrobi100_module10/index.jsp -->

    <title>BHC</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    
    <!-- Using Twitter Bootstrap for CSS Styling basics -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
    
    <!-- Custom CSS Styling -->
    <style type="text/css">
      .navbar a {color:white}
      li {text-align: right; list-style: none}
      td {text-align: center}
      thead tr td {font-weight: bold}
      
    </style>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <!--<link rel="stylesheet" href="/resources/demos/style.css">-->
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="index.js"></script>
    
    <jsp:useBean id="hikesubmit" class="edu.jhu.mrobi100.Hike" scope="request"/>
  </head>
  
  <body>
    <!-- Nav -->
    <div class="nav navbar navbar-dark bg-dark">
      <a class="nav-link" href="https://web7.jhuep.com/mrobi100_module11/index.jsp">Home</a>
      <a class="nav-link" href="https://web7.jhuep.com/mrobi100_module11/admin.jsp">Admin</a>
      <a class="pull-right" href="https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899">
        Visit the Forest Service's Page
      </a>
    </div>

    <br>

    <!-- Hikes & Image Body -->
    <div class="container">
      <div class="row"> 
        <div class="col-md-12">
            <h2 style="text-align:center">Welcome to Beartooth Hiking Company (BHC)</h2>
        </div>
      </div>

      <br>

      <div class="row">
        <div class="col-md-6">
            <img class="img-fluid" alt="A picture of a train on a hill" src="https://web7.jhuep.com/~evansrb1/en605681/Images/TetonTrail-2_800x600.jpg">
        </div>
        <div class="col-md-6">
          <h3 style="text-align: right">Tour Cost Calculator</h3>

          <!--Calculator-->
          <div class="col-md">
            <form action="BhcControllerAdmin" method=GET>
              <div class="form-row">
                <div class="form-group col-md-12">
                  <p>Start Date
                    <label for="datepicker"></label>
                    <input class="form-control" id="datepicker" name="datepicker">
                  </p>
                </div>
              </div>

              <jsp:setProperty name="reservationDates" property="*" />
              <div class="text-right">
                <button type="submit" class="btn btn-primary">Submit</button>
              </div>
            </form>
          </div>


        </div>
      </div>
      <br>

      <!-- Tours -->
      <div class="row"> 
        <div class="col-md-12">
            <h2>Tours</h2>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <table class="table">
            <thead>
              <tr>
                <td>Hike</td>
                <td>Duration (Days)</td>
                <td>Difficulty</td>
                <td>Price</td>
                <td>Surcharge</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td> Gardiner Lake </td>
                <td> 3 or 5 </td>
                <td> Intermediate </td>
                <td> $40/day </td>
                <td> +50% for Sat/Sun Hikes </td>
              </tr>
              <tr>
                <td> Hellroaring Plateau </td>
                <td> 2, 3, or 4 </td>
                <td> Easy </td>
                <td> $35/day </td>
                <td> +50% for Sat/Sun Hikes </td>
              </tr>
              <tr>
                <td> The Beaten Path </td>
                <td> 5 or 7 </td>
                <td> Difficult </td>
                <td> $45/day </td>
                <td> +50% for Sat/Sun Hikes </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="footer">
          <div class="text-muted" style="text-align: center">Using 
            <a href="https://getbootstrap.com/">Bootstrap</a>
            for starting CSS
          </div>
      </div>
    </div>
  </body>
</html>
