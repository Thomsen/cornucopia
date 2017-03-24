LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := copia-patch
LOCAL_SRC_FILES := patch.c \
	
LOCAL_STATIC_LIBRARIES := \
	libcopia-bzip2
	
LOCAL_LDLIBS := -lz -llog

include $(BUILD_SHARED_LIBRARY)
