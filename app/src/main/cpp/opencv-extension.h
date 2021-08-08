//
// Created by xchel on 19/07/2021.
//
#pragma once

#include "opencv-utils.h"

void bitmapToMat(JNIEnv *env, jobject bitmap, Mat &dst, jboolean needUnPremultiplyAlpha);
void matToBitmap(JNIEnv *env, Mat src, jobject bitmap, jboolean needPremultiplyAlpha);
