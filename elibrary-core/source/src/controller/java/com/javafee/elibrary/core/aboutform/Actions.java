package com.javafee.elibrary.core.aboutform;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.javafee.elibrary.core.common.Common;
import com.javafee.elibrary.core.common.SystemProperties;
import com.javafee.elibrary.core.common.action.IActionForm;
import com.javafee.elibrary.core.common.dto.Build;
import com.javafee.elibrary.hibernate.dao.HibernateUtil;
import com.javafee.elibrary.hibernate.dao.common.Constants;
import com.javafee.elibrary.hibernate.dto.library.LibraryBranchData;
import com.javafee.elibrary.hibernate.dto.library.LibrarySystemData;

public class Actions implements IActionForm {
	private AboutForm aboutForm;

	public void control() {
		openAboutForm();
		initializeForm();
	}

	private void openAboutForm() {
		if (aboutForm == null || (aboutForm != null && !aboutForm.getFrame().isDisplayable())) {
			aboutForm = new AboutForm();
			aboutForm.getFrame().setVisible(true);
		} else {
			aboutForm.getFrame().toFront();
		}
	}

	@Override
	public void initializeForm() {
		openAboutForm();
		reloadLblsDynamic();
	}

	private void reloadLblsDynamic() {
		reloadLblVersion();
		reloadLblLicenseInformation();
		reloadLblSubscription();
	}

	private void reloadLblVersion() {
		Build build = Common.fetchSystemVersion();
		String version = build.getSourceBlob().getVersion();
		SimpleDateFormat versionDateFormat = new SimpleDateFormat("yyyy-dd-MM"),
				releaseDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		String versionDateString = versionDateFormat.format(build.getCreatedAt()),
				releaseDateString = releaseDateFormat.format(build.getCreatedAt());
		String versionText = MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.lblVersion"),
				"#DSK-" + versionDateString + "-" + version, releaseDateString);
		aboutForm.getAboutPanel().getLblVersion().setText(versionText);
		aboutForm.getAboutPanel().getLblVersion().setToolTipText(versionText);
	}

	private void reloadLblLicenseInformation() {
		LibrarySystemData librarySystemData = HibernateUtil.getSession().get(LibrarySystemData.class, Constants.DATA_BASE_LIBRARY_DATA_ID);
		LibraryBranchData libraryBranchData = (LibraryBranchData) librarySystemData.getLibraryData().getLibraryBranchData().toArray()[0];
		String licenseText = MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.lblLicenseInformation"),
				librarySystemData.getLibraryData().getName(), libraryBranchData.getName());
		aboutForm.getAboutPanel().getLblLicenseInformation().setText(licenseText);
	}

	private void reloadLblSubscription() {
		//TODO Mocked - license not handled yet, maybe apiId could be used
		String subscriptionText = MessageFormat.format(SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.lblSubscription"),
				SystemProperties.getInstance().getResourceBundle().getString("aboutPanel.isActive"), "");
		aboutForm.getAboutPanel().getLblSubscription().setText(subscriptionText);
	}
}
