package com.cornucopia.storage.ticketsmanager;

import com.cornucopia.application.CornucopiaApplication;

import android.app.Activity;

public class TicketsManagerActivity extends Activity {
	
	//
	public TicketsManagerActivity() {
		super();
	}

	protected CornucopiaApplication getTicketsManagerApplication() {
		// 获取Application，通过getApplicaiton()，而不是new
		CornucopiaApplication tma = (CornucopiaApplication) getApplication();
		return tma;
	}

	

}
