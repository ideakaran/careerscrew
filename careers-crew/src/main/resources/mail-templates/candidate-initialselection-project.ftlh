<!DOCTYPE html>
<html>
<head>
<#--    todo
        replace Role by actual variable
        replace Sunday EOD 30th Dec, 2021 by automatic generated date
        check if task description can be pulled from task table
-->
<title>Project Assignment | ABC | ####Role####</title>
</head>
<body>
	<h3>Dear ${fullName!''}</h3>

	<p>As per our interview process, we would like to request you to take a project assignment. As discussed, please complete it within 3 days
    and send it back to me by <b>${assessmentTime}</b></p>

    <br />
    <br />
    <p>Please share the code through <b>Github</b> and you can choose any frontend technology that you have worked on in your past experience
        to complete  this assignment.<p>
    <br/>
    <p>Thanking you in advance and looking forward to receiving your submissiosn.</p>
    <br/>
    <h3>Task Detail:</h3>
    <@description?interpret />
    <br />
    <b>Please free to reach out to us for any queries.</b>
    <br />
Thanks,


</body>
</html>
