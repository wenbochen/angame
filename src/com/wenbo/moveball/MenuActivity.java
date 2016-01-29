package com.wenbo.moveball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memu);
		context = this;
		Button movebutton = (Button) findViewById(R.id.button1);
		movebutton.setOnClickListener(movelistener);
	}
	
	private OnClickListener movelistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(context,  MoveBallActivity.class);
			startActivity(intent);
		}
	};
}
