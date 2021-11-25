
export interface VerifyForgotPasswordUpdateContext {
    email: string;
    forgotPasswordVerCode: string;
    newPassword: string;
}

export interface LoginContext {
    email: string;
    password: string;
    rememberMe?: boolean;
}
