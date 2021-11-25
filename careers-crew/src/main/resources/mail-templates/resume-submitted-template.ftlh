<!DOCTYPE html>
<html>
<head>
    <title>Resume Submitted</title>
</head>
<body>
<h3>Hello ${fullName!''}</h3>

<p>
    We have received your profile for the position of <strong>${jobPosition}</strong> at <strong>${companyBrandName}</strong>.
    Your application is under processing.
</p>
<p>We will get back to you soon.</p>

<br/>

<p>
    You can now track your application status from our application.
    <#assign loginLink>${trackingSite}</#assign>
    <a href=${loginLink} rel="link">${loginLink}</a>
<p>
<p>Below is your credentials</p>
<strong>Username:</strong> &quot;${userEmail}&quot;
<br/>
<strong>Pass:</strong> &quot;${generatedPass}&quot;

<br>
<p>If this wasn't you, please simply ignore this email. </p>

</body>
</html>
