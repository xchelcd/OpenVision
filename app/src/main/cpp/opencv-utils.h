//
// Created by xchel on 19/07/2021.
//
#pragma once

#include <opencv2/core.hpp>


using namespace cv;

//Declare functions
//void functionsName(params...);
void doBlur(Mat src, float sigma);
void testingLibs(const Mat& src, Mat drawing);
