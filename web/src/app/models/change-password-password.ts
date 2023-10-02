export interface ChangePasswordRequest {
  token: string;
  newPassword: string;
  confirmNewPassword: string;
}
