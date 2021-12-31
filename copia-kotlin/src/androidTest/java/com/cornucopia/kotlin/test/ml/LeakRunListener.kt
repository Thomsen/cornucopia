package com.cornucopia.kotlin.test.ml

import leakcanary.FailTestOnLeakRunListener
import org.junit.runner.Description

class LeakRunListener: FailTestOnLeakRunListener() {

    override fun skipLeakDetectionReason(description: Description): String? {
        return if (description.getAnnotation(LeakTest::class.java) != null) {
            null
        } else {
            "skip leak test"
        }
    }
}