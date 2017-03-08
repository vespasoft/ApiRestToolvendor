<%-- 
    Document   : form
    Created on : Nov 17, 2016, 3:42:54 PM
    Author     : luisvespa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <p>Servlet 3.0 File Upload</p>
        <form action="upload" enctype="multipart/form-data" method="POST">
            <input type="file" name="file1"><br>
            <input type="file" name="file1"><br>
            <input type="file" name="file1"><br>
            <input type="file" name="file1"><br>
            <input type="text" id="repository" name="repository" size=20><br>
            <input type="Submit" value="Upload File"><br>
        </form>
    </body>
</html>
