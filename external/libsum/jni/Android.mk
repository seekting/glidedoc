LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE:= sum
LOCAL_SRC_FILES := sum.c \
                   com_qihoo_mm_liba_LibSum.c
include $(BUILD_SHARED_LIBRARY)