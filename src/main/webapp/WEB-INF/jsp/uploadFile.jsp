<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="css/custom.css" rel="stylesheet">
    <title>File Upload Form</title>
</head>

<body>
<div class='main'>
<h1>Color Balance Calculator</h1>

<div class='form'>
<form method="post" action="/upload" enctype="multipart/form-data">
    <label for="file">Choose a text file:</label>
    <input type="file" name="file" id="file" accept=".txt">
    <input class='btn' type="submit" value="Calculate">
</form>
<button class='btn' onclick="removeFile()">Clear Output</button>
</div>

<% if (request.getAttribute("output") != null) { %>
    <div id="outputDiv">
      <h3>Output:</h3>
      <pre><%= request.getAttribute("output") %></pre>
    </div>
<% } %>

<% if (request.getAttribute("error") != null) { %>
    <p class='err'><%= request.getAttribute("error") %></p>
<% } %>

<script>
    function removeFile() {
        document.getElementById('outputDiv').innerHTML = '';
    }
</script>

</div>
</body>
</html>
