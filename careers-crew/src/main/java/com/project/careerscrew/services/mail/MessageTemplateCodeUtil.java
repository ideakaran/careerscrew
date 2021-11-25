package com.project.careerscrew.services.mail;

public class MessageTemplateCodeUtil {


    public enum TemplatesPath {
        PASSWORD_RESET_TEMPLATE("/password-reset-template.ftlh"),

        RESUME_SUBMITTED_TEMPLATE("/resume-submitted-template.ftlh"),
        CANDIDATE_INITIAL_SELECTION_PROJECT("/candidate-initialselection-project.ftlh"),
        VERIFY_REFERRAL_TEMPLATE("/verify-referral.ftlh"),
        INTERVIEW_SCHEDULED_TEMPLATE("/user-interview-scheduled-template.ftlh"),
        INTERVIEW_SELECTION_STATUS_TEMPLATE("/candidate-selected-template.ftlh"),
        INTERVIEW_SELECTION_REFFERER_STATUS_TEMPLATE("/candidate-selected-reffer-template.ftlh"),
        INTERVIEW_UNSELECTION_STATUS_TEMPLATE("/candidate-unselected-template.ftlh"),
        INTERVIEW_UNSELECTION_REFFERER_STATUS_TEMPLATE("/candidate-unselected-reffer-template.ftlh"),
        EMAIL_HR_FOR_REFERER_BONUS("/email-humanresource-for-referral-bonus.ftlh"),
        EMAIL_HR_AS_INTERVIEW_PASSED("/email-humanresource-as-interview-passed.ftlh"),
        EMAIL_HR_AS_INTERVIEW_FAILED("/email-humanresource-as-interview-failed.ftlh"),
        REFERER_BONUS_TEMPLATE("/referer-bonus-template.ftlh"),
        EMAIL_CANDIDATE_FOR_INTERVIEW("/email-to-candidate-for-interview.ftlh"),
        EMAIL_INTERVIEWER_FOR_INTERVIEW("/email-to-interviewers.ftlh"),
        MESSAGE_COMMUNICATION_TEMPLATE("/message-communication-template.ftlh");

        private String templatePath;

        TemplatesPath(String templatePath) {
            this.templatePath = templatePath;
        }

        public String getTemplatePath() {
            return templatePath;
        }
    }

    public static class TemplateKeys {

        // template Keys
        public static final String fullName = "fullName";
        public static final String referenceId = "referenceId";
        public static final String generatedPass = "generatedPass";

        public static final String linkForgotPasswordReset = "linkForgotPasswordReset";
        public static final String linkForReferrerVerification = "linkForReferrerVerification";

        public static final String trackingSite = "trackingSite";

    }

    // Email Subject constant
    public static final String subjectForgotPasswordResetProcess = "Forgot Password - Reset process";
    public static final String subjectResumeReceived = "We have received your resume";


}
