package com.cornucopia.ticketsmanager;

import android.app.Activity;

public class TicketsManagerActivity extends Activity {
	
	//
	public TicketsManagerActivity() {
		super();
	}

	protected TicketsManagerApplication getTicketsManagerApplication() {
		// 获取Application，通过getApplicaiton()，而不是new
		TicketsManagerApplication tma = (TicketsManagerApplication) getApplication();
		return tma;
	}

	

}
