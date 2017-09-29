package com.myxlab.bustracker.View.Login;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;


public class SignUp_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText fullName, emailId, mobileNumber, location,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;
	private View mProgressView;
	private static Animation shakeAnimation;
	private static MainLoginActivity mainLoginActivity;

	public SignUp_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.signup_layout, container, false);
		initViews();
		setListeners();
		mainLoginActivity = (MainLoginActivity) getActivity();
		return view;
	}

	// Initialize all views
	private void initViews() {
		fullName = (EditText) view.findViewById(R.id.fullName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
		mProgressView = view.findViewById(R.id.login_progress);
		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			new MainLoginActivity().replaceLoginFragment();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFullName = fullName.getText().toString();
		String getEmailId = emailId.getText().toString();

		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFullName.equals("") || getFullName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"All fields are required.");

		else if (!(getEmailId.contains("gmail") || getEmailId.contains("siswa.ukm.edu.my") || getEmailId.contains("ukm.edu.my"))) {
			new CustomToast().Show_Toast(getActivity(), view,
					"Please use a Gmail or UKM email");
            emailId.startAnimation(shakeAnimation);
		}
		// Check if email id valid or not
		else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
            emailId.startAnimation(shakeAnimation);
        }
		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");
            password.startAnimation(shakeAnimation);
            confirmPassword.startAnimation(shakeAnimation);
        }
		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");
            terms_conditions.startAnimation(shakeAnimation);
        }
		// Else do signup or do your stuff
		else {
			String username  = getEmailId.substring(0, getEmailId.indexOf("@"));
			mProgressView.setVisibility(View.VISIBLE);
			UserInstance.getInstance().getVolleyApp().UserRegistrationTask(getString(R.string.url_signup), username, getPassword,getFullName,getEmailId, getActivity(), mProgressView, mainLoginActivity);
		}



	}

	/*private void attemptLogin() {

		mUsernameView.setError(null);
		mPasswordView.setError(null);

		String username = mUsernameView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		} else if (!isPasswordValid(password)) {
			mUsernameView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (!isUsernameValid(username)) {
			mUsernameView.setError(getString(R.string.error_invalid_email));
			focusView = mUsernameView;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {
			mProgressView.setVisibility(View.VISIBLE);
			UserInstance.getInstance().getVolleyApp().UserLoginTask(getString(R.string.url_login), username, password, getActivity(), mProgressView, mainLoginActivity);
		}
	}

	private boolean isUsernameValid(String email) {
		return email.length() > 3;
	}

	private boolean isPasswordValid(String password) {
		return password.length() > 3;
	}*/
}
