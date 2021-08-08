//
// Created by xchel on 19/07/2021.
//
#include "opencv-utils.h"
#include <opencv2/imgproc.hpp>
#include <string>

using namespace std;

//Write functions
//void functionsName(params...) { }
void doBlur(Mat src, float sigma) {
    int sigmaInt = (int) sigma;
    int thickness = 3;
    int offset = 50;
    int offsetX = offset + thickness;
    int offsetY = offset + thickness;
    int internalRadius = offset;
    int externalRadius = (int) sqrt(2 * (offsetX * offsetY)) + 2 * thickness;

    RNG rng(12345);
    Scalar color_1(255, 247, 0);
    Scalar color_2(rng.uniform(0, 256), rng.uniform(0, 256), rng.uniform(0, 256));
    Scalar rColor(255, 0, 0);
    Scalar gColor(0, 255, 0);
    Scalar bColor(0, 0, 255);

    int widht = src.cols;
    int height = src.rows;

    rectangle(src, Point(widht / 2 - offsetX, height / 2 - offsetY),
              Point(widht / 2 + offsetX, height / 2 + offsetY), bColor, thickness);
    //GaussianBlur(src, src, Size(), sigma);
    putText(src, "k: " + to_string(sigmaInt), Point(50, 50), FONT_HERSHEY_SIMPLEX, 1,
            gColor, 5, LINE_8);
    circle(src, Point(widht / 2, height / 2), internalRadius, rColor, thickness);
    circle(src, Point(widht / 2, height / 2), externalRadius, color_2, thickness);
    GaussianBlur(src, src, Size(), sigma);
    cvtColor(src, src, COLOR_BGR2GRAY);
}

void testingLibs(const Mat& src, Mat drawing) {
    RNG rng(12345);
    Mat canny;
    Canny(src, canny, 100, 200);
    vector<vector<Point> > contours;
    vector<Vec4i> hierarchy;
    findContours(canny, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
    drawing = Mat::zeros( canny.size(), CV_8UC3 );
    for( size_t i = 0; i< contours.size(); i++ )
    {
        Scalar color = Scalar( rng.uniform(0, 256), rng.uniform(0,256), rng.uniform(0,256) );
        drawContours( drawing, contours, (int)i, color, 2, LINE_8, hierarchy, 0 );
    }
}