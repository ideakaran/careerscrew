package com.project.careerscrew.utils.exceptions;

public final class AppExceptionConstants {

    public static final String INTERNAL_EXCEPTION = "Something went wrong, Internal Exception";
    public static final String INVALID_REQUEST = "Something went wrong, Invalid Request !!!";
    public static final String MAIL_SENDING_EXCEPTION = "Something went wrong, While Trying to send an email";
    public static final String NOT_IMPLEMENTED_YET = "Not implemented yet !!!";
    public static final String INTERNAL_IO_EXCEPTION = "Something went wrong, Internal IO Exception";
    public static final String CANNOT_UPLOAD_EMPTY_MULTIPART_FILE = "Internal Exception, Cannot upload empty multipart file";

    // Auth Exception
    public static final String BAD_LOGIN_CREDENTIALS = "Bad Credentials - Invalid username or password";

    public static final String UNAUTHORIZED_ACCESS = "UnAuthorized Access";
    public static final String FORBIDDEN_ACCESS = "Insufficient privilege";

    // User Exception
    public static final String YOUR_APPLICATION_IS_UNDER_PROCESSING = "Your application is currently under processing. Please wait for the response.";
    public static final String THIS_EMAIL_IS_NOT_AVAILABLE_FOR_PROCESSING = "Sorry, This email isn't available for processing.";

    public static final String USER_RECORD_NOT_FOUND = "User doesn't exists";
    public static final String USER_EMAIL_NOT_AVAILABLE = "This email isn't available";

    public static final String RECORD_NOT_FOUND = "Record not found";

    // Raw-Data Exception
    public static final String FILE_FORMAT_NOT_SUPPORTED = "Sorry, this file format isn't supported";
    public static final String REQUESTED_RESOURCE_NOT_FOUND = "Couldn't find the requested resource";
    public static final String REQUESTED_CANDIDATE_FILE_RECORD_NOT_FOUND = "Couldn't find the requested candidate file record";

    public static final String DOWNLOADABLE_RESOURCE_NOT_FOUND = "Downloadable resource not available";
}
