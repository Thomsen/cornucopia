package com.cornucopia.di.dagger2;

import javax.inject.Singleton;

import com.cornucopia.application.CornucopiaApplication;

import dagger.Component;

/**
 * 将 @Inject 和 @Module 的 dependency 产生直接的连接
 */
@Singleton
@Component(modules = {D2Module.class})
public interface D2GraphComponent {

    void inject(D2MainActivity d2MainActivity);
    
    static final class Initializer {
        private Initializer() {
            
        }
        
        public static D2GraphComponent init(CornucopiaApplication app) {
            return DaggerD2GraphComponent.builder().d2Module(new D2Module(app)).build();
        }
    }
}
