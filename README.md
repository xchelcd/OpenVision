# OpenVision
Android Jetpack(CameraX, NavComponent, Room, etc), OpenCV ndk,  Google Maps, Retrofit(request to API), Material Design, Android styles, themes, anim, etc.

## Camera Fragment
From **Android** **Jetpack** show the camera to take photos

## OpenCV Fragment
With **CameraX**, retrieve each frame for rendering in C ++ and manipulating Mat object with **OpenCV**<br/>
### Steps:
 1- Retrieve every frame (bitmap) from camera<br/>
 2- Send the bitmap to a native function written in C++<br/>
 3- Obtain a Mat object from the bitmap<br/>
 4- Process the Mat object<br/>
 5- Retrieve a bitmap of the previously processed Mat object<br/>
 6- Return bitmap and display it in an ImageView asynchronously


## Maps Fragment 
Maps Fragment

## Room Fragment
**Create**, **Read**, **Update** and **Delete** a object User based in **Room from Android Jetpack**.<br/>
Also can collapse/expand crud's menu, with a nice animations, to see all user of better way. Select an user to view detailed info.<br/>
The entire database is local

## Dynamic Fragment
Solve a square matrix by the gauss jordan method<br/>
This fragment has 3 layouts: **header**, **square matrix** and **solution**.<br/>
All the views inside each layouts are created **dynamically**.<br/>
#### Check the file xml: https://github.com/xchelcd/OpenVision/blob/master/app/src/main/res/layout/fragment_dynamic.xml

## API Rest Fragment
API Rest Fragment
