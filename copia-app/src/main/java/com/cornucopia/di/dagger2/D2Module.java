package com.cornucopia.di.dagger2;

import javax.inject.Singleton;

import android.app.Application;

import com.cornucopia.application.CornucopiaApplication;

import dagger.Module;
import dagger.Provides;

/**
 * 产生一系列相关的 dependency
 */
@Module
public class D2Module {
    
    CornucopiaApplication app; 
    
    public D2Module(CornucopiaApplication app) {
        this.app = app;
    }
    
    @Provides
    @Singleton
    protected Application provideApplication() {
        return app;
    }
    
    @Provides
    @Singleton
    protected D2Service provideD2Service() {
        return new D2ServiceManager();
    }

    @Provides
    @Singleton
    D2CollectionUtils provideStringUtils() {
        return new D2CollectionUtils();
    }
}
