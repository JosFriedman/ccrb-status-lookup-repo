<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<html>
  <head><title>Index</title></head>
  <body>
    <h1>Index</h1>
    <h5>Captcha Status: ${model.disablecaptcha} ${model.disablecaptchay}</h5>
    <h5>Redirect Form URL: ${model.redirectformurl}</h5>
    <h4>Available Links:</h4>
    <ul>
    <li><a href="case/view">Case (GET)</a></li>
    <li><a href="case/form">Case (form POST to case/view)</a></li>
    </ul>
</html>