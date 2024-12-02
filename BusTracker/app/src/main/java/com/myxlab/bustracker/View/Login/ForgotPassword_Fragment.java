package com.myxlab.bustracker.View.Login;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;


public class ForgotPassword_Fragment extends Fragment implements
		OnClickListener {
	private static View view;
	private View mProgressView;
	private static EditText emailId;
	private static TextView submit, back;

	private static MainLoginActivity mainLoginActivity;
	private static ForgotPassword_Fragment forgotPassword_fragment;
	private static Animation shakeAnimation;

	public ForgotPassword_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forgotpassword_layout, container,
				false);
		initViews();
		setListeners();
		mainLoginActivity = (MainLoginActivity) getActivity();
		return view;
	}

	// Initialize the views
	private void initViews() {
		emailId = (EditText) view.findViewById(R.id.registered_emailid);
		submit = (TextView) view.findViewById(R.id.forgot_button);
		back = (TextView) view.findViewById(R.id.backToLoginBtn);
		mProgressView = view.findViewById(R.id.login_progress);
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);


		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			back.setTextColor(csl);
			submit.setTextColor(csl);

		} catch (Exception e) {
		}

	}

	// Set Listeners over buttons
	private void setListeners() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backToLoginBtn:

			// Replace Login Fragment on Back Presses
			new MainLoginActivity().replaceLoginFragment();
			break;

		case R.id.forgot_button:

			// Call Submit button task
			submitButtonTask();
			break;

		}

	}

	private void submitButtonTask() {
		String getEmailId = emailId.getText().toString();
		boolean cancel = false;
		View focusView = null;
		// Pattern for email id validation
		Pattern p = Pattern.compile(Utils.regEx);

		// Match the pattern
		Matcher m = p.matcher(getEmailId);

		// First check if email id is not null else show error toast
		if (getEmailId.equals("") || getEmailId.length() == 0) {
			new CustomToast().Show_Toast(getActivity(), view,
					"Please enter your Email Id.");
			emailId.startAnimation(shakeAnimation);
		}

		// Check if email id is valid or not
		else if (!m.find()) {
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");
			emailId.startAnimation(shakeAnimation);
		}

		// Else submit email id and fetch passwod or do your stuff
		/*else
			Toast.makeText(getActivity(), "Check Email to reset your Password",
					Toast.LENGTH_SHORT).show();*/
	else {
				mProgressView.setVisibility(View.VISIBLE);
				UserInstance.getInstance().getVolleyApp().Forgot(getString(R.string.url_forgot_password), getEmailId, getActivity(), mProgressView, mainLoginActivity, this);
			}
		}



	public void createCustomToast(String s){
		new CustomToast().Show_Toast(getActivity(), view,
				s);
	}
}