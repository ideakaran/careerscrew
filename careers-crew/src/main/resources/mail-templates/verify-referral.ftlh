<!DOCTYPE html>
<html>
<head>
    <title>Verify your referred candidate</title>
</head>
<body>
<h3>Hello ${fullName!''}</h3>

<p>We have received an application from one of the candidate named:  <strong>${applicantName}</strong>.
    <strong>${applicantName}</strong> has applied for <strong>${jobTitle}</strong> and mentioned you as a referer.
</p>

<p>Please click this link to verify that this is a correct info.
    <a href=${linkForRefererToVerifyCandidate} rel="link">${linkForRefererToVerifyCandidate}</a>
</p>

<br/>
<h3>Application Details</h3>
<p><strong>Applicant Name:</strong> ${applicantName} </p>
<p><strong>Applicant Email:</strong> ${applicantEmail} </p>
<p><strong>Applied Job Position:</strong> ${jobTitle} </p>

<br/>
<p>If this wasn't you or this info is not invalid, please simply ignore this email. </p>

</body>
</html>
