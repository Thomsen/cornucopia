package com.cornucopia.application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import java.util.ArrayList;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.bugsnag.android.Bugsnag;
import com.cornucopia.application.exception.AppBlockCanaryContext;
import com.cornucopia.aspect.dexposed.DexposedHook;
import com.cornucopia.di.dagger2.D2GraphComponent;
import com.cornucopia.hotfix.Hotfix;
import com.cornucopia.storage.ticketsmanager.Tickets;
import com.cornucopia.storage.ticketsmanager.TicketsSQLiteOpenHelper;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class CornucopiaApplication extends MultiDexApplication {

	private ArrayList<Tickets> currentTickets;

	private TicketsSQLiteOpenHelper ticketDBHelper;
	
	private static D2GraphComponent graph;
	
	private static RefWatcher refWatcher;
	
	private static CornucopiaApplication instance;
	
	@Override
	protected void attachBaseContext(Context base) {
	    super.attachBaseContext(base);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		
	    // 异常捕获 
        initCrashHandler();
		
		// 数据库操作
		ticketDBHelper = new TicketsSQLiteOpenHelper(this);
		
		if (null == currentTickets) {
//			currentTickets = new ArrayList<Tickets>();
			
			// 使用数据库
			currentTickets = ticketDBHelper.loadTickets();
		}
		
		Hotfix hotfix = new Hotfix();
		hotfix.loadBugfix(this, "patch_dex.jar", "com.cornucopia.hotfix.HotfixBug");
		
		DexposedHook dexposed = new DexposedHook();
		dexposed.hook(this);
		
		buildComponentGraph();
		
//		initRealmInstance();
		
		refWatcher = LeakCanary.install(this);
		
		// no found class
//		AppBlockCanaryContext appBlock = new AppBlockCanaryContext(); 
//		BlockCanary.install(this, appBlock).start();
	}

    private void initCrashHandler() {
        Bugsnag.init(this);
        
//        MetaData metaData = new MetaData();
//        metaData.addToTab("User", "username", "thom");
//        Bugsnag.notify(new Exception("Non-fatal"), Severity.INFO,  metaData);
	}

	// 设置和获取当前的ticket
	public ArrayList<Tickets> getCurrentTickets() {
		return currentTickets;
	}

	public void setCurrentTickets(ArrayList<Tickets> currentTicket) {
		this.currentTickets = currentTicket;
	}
	
	// 添加ticket
	public void addTicket(Tickets ticket) {
		//
		assert(null != ticket);
		
		// 使用对象前最好对对象进行非空判断
		if (null == currentTickets) {
			currentTickets = new ArrayList<Tickets>();
		}
		
		currentTickets.add(ticket);
	}

	public TicketsSQLiteOpenHelper getTicketDBHelper() {
		return ticketDBHelper;
	}

	
    private void buildComponentGraph() {
        graph = D2GraphComponent.Initializer.init(instance);
    }
    
    public static D2GraphComponent component() {
        return graph;
    }
    
//    private void initRealmInstance() {
//        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(config);
//    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

}
