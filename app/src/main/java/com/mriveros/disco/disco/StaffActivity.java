package com.mriveros.disco.disco;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.mriveros.disco.R;


public class StaffActivity extends Activity {
	Activity a;
	Context context;


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        a=this;
        context=getApplicationContext();

    	
	}

}