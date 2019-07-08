package com.javafee.settingsform;

import com.javafee.common.*;
import com.javafee.exception.LogGuiException;
import com.javafee.hibernate.dao.HibernateUtil;
import com.javafee.hibernate.dto.common.UserData;
import com.javafee.startform.LogInEvent;
import lombok.Setter;

import javax.swing.*;

public class PasswordChangePanelEvent implements IActionForm {
    @Setter
    private SettingsForm settingsForm;

    protected static PasswordChangePanelEvent passwordChangePanelEvent = null;

    private PasswordChangePanelEvent(SettingsForm settingsForm) {
        this.control(settingsForm);
    }

    public static PasswordChangePanelEvent getInstance(SettingsForm settingsForm) {
        if (passwordChangePanelEvent == null)
            passwordChangePanelEvent = new PasswordChangePanelEvent(settingsForm);

        return passwordChangePanelEvent;
    }

    public void control(SettingsForm settingsForm) {
        setSettingsForm(settingsForm);
        initializeForm();

        settingsForm.getSettingsPanel().getPasswordChangePanel().getBtnAccept().addActionListener(e -> onClickBtnAccept());
    }

    public void initializeForm() {
    }

    private void onClickBtnAccept() {
        if (validatePasswordChange()) {
            if (Utils.displayConfirmDialog(
                    SystemProperties.getInstance().getResourceBundle().getString("confirmDialog.continueQuestion"),
                    "") == JOptionPane.YES_OPTION)
                persistPasswordChange();
        }
    }

    private void persistPasswordChange() {
        String newPassword = settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().toString();
        UserData userData = LogInEvent.getUserData();
        userData.setPassword(Common.createMd5(newPassword));

        HibernateUtil.beginTransaction();
        HibernateUtil.getSession().update(UserData.class.getName(), userData);
        HibernateUtil.commitTransaction();

        Utils.displayOptionPane(
                com.javafee.common.SystemProperties.getInstance().getResourceBundle()
                        .getString("passwordChangePanelEvent.passwordChangeSuccess"),
                com.javafee.common.SystemProperties.getInstance().getResourceBundle()
                        .getString("passwordChangePanelEvent.passwordChangeSuccessTitle"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearPasswordChangeFailsParams() {
        Params.getInstance().remove("WEAK_PASSWORD");
    }

    private boolean validateNewAndConfirmedPasswords(char[] newPassword, char[] confirmedPassword) {
        return newPassword != null && confirmedPassword != null && newPassword.toString().equals(confirmedPassword.toString());
    }

    private boolean validatePasswordChange() {
        boolean passwordFiledOldEmpty = settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldOld().getPassword() == null,
                passwordFiledNewEmpty = settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldNew().getPassword() == null,
                passwordFieldConfirmNewEmpty = settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().getPassword() == null,
                validateNewAndConfirmedPasswords = !passwordFiledNewEmpty && !passwordFieldConfirmNewEmpty
                        && !validateNewAndConfirmedPasswords(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldNew().getPassword(),
                        settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().getPassword()),
                newPasswordStrength = validateNewAndConfirmedPasswords
                        && Common.checkPasswordStrength(settingsForm.getSettingsPanel().getPasswordChangePanel().getPasswordFieldConfirmNew().toString());
        StringBuilder errorBuilder = new StringBuilder();

        if (passwordFiledOldEmpty)
            errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
                    .getString("passwordChangePanelEvent.passwordChangeError1"));
        else if (passwordFiledNewEmpty)
            errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
                    .getString("passwordChangePanelEvent.passwordChangeError2"));
        else if (passwordFieldConfirmNewEmpty)
            errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
                    .getString("passwordChangePanelEvent.passwordChangeError3"));
        else if (!validateNewAndConfirmedPasswords)
            errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
                    .getString("passwordChangePanelEvent.passwordChangeError4"));
        else if (!newPasswordStrength)
            errorBuilder.append(SystemProperties.getInstance().getResourceBundle()
                    .getString("startForm.registrationError7")); // WEAK_PASSWORD resource
        else {
            clearPasswordChangeFailsParams();
            return true;
        }

        LogGuiException.logError(SystemProperties.getInstance().getResourceBundle()
                .getString("passwordChangePanelEvent.passwordChangeErrorTitle"), errorBuilder.toString());
        clearPasswordChangeFailsParams();

        return false;
    }
}
