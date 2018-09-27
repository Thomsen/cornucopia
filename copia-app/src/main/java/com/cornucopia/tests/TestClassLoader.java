package com.cornucopia.tests;


//import java.net.URL;
//
//import junit.framework.TestCase;
//
//public class TestClassLoader extends TestCase {
//
//	// bootstrap classloader 原始类加载， 是extension父类
//	public void testBootstrap() {
//
////		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
////		for (int i=0; i<urls.length; i++) {
////			System.out.println(i + "\t" + urls[i].toExternalForm());
////		}
//
//	}
//
//	// extension classloader 扩展类加载，是system父类
//	public void testExtension() {
//		System.out.println(System.getProperty("java.ext.dirs"));
//		ClassLoader extensionClassLoader = ClassLoader.getSystemClassLoader().getParent();
//		System.out.println("extension classloader : " + extensionClassLoader);
//		System.out.println("the parent of extension classloader : " + extensionClassLoader.getParent());
//
//	}
//
//	// system (application) classloader 系统（应用）类加载
//	public void testSystem() {
//		System.out.println(System.getProperty("java.class.path"));
//		System.out.println("system classloader : " + ClassLoader.getSystemClassLoader());
//
//		System.out.println(System.class.getClassLoader());
//
////		System.out.println(sun.misc.Launcher.class.getClassLoader());
//
//		System.out.println(ClassLoader.getSystemResource("java/lang/String.class"));
//	}
//
//	// context classloader, setContextClassLoader(), getContextClassLoader()
//}
