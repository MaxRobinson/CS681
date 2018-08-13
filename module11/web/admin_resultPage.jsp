<%-- 
    Document   : index
    Created on : Aug 5, 2018, 3:55:52 PM
    Author     : max
--%>

<%@page import="edu.jhu.mrobi100.ReservationDTO"%>
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
      li {text-align: left; list-style: none}
      .center td {text-align: center}
      thead tr td {font-weight: bold}
      
    </style>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <!--<link rel="stylesheet" href="/resources/demos/style.css">-->
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="index.js"></script>
    <jsp:useBean id="reservations" class="edu.jhu.mrobi100.Result" scope="request" />
    
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
      </div>
      <br>

      <!-- Tours -->
      <div class="row"> 
        <div class="col-md-12">
            <h2>Reservations</h2>
        </div>
      </div>
      <div class="row">
        <!--Calculator-->
        <div class="col-md-12 center">
            <% 
              if(reservations.getResults() == null){
            %>
            <h4>Incorrect Date Entered</h4>
            <%
            } else {
            %>
            <div>
                <table class="table">
                    <thead>
                        <tr>
                            <td>Start Date</td>
                            <td>End Date</td>
                            <td>Reserver</td>
                            <td>Location</td>
                            <td>Guide</td>
                        </tr>
                    </thead>
                    <tbody>
            <%
                for(ReservationDTO reserve : reservations.getResults()){
                    %>
                        <tr>
                            <td> <%=reserve.getStartDate()%></td>
                            <td> <%=reserve.getEndDate()%></td>
                            <td> <%=reserve.getFirstName()%> <%=reserve.getLastName()%></td>
                            <td> <%=reserve.getLocation()%></td>
                            <td> <%=reserve.getGuideFirstName()%> <%=reserve.getGuideLastName()%></td>
                        </tr>
                    <%
                }
            }
            %>
                    </tbody>
                </table>
            </div>
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
