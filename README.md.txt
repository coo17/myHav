# myHa &ensp; [![Generic badge](https://img.shields.io/badge/version-%201.0.4-blue?style=for-the-badge&logo=android)](https://shields.io/)

> It is a habit tracker enabling users to develop good habits. It not only has the function of establishing personal habits, but also can find habitual friends through the concept of group interaction.

[<img width="200" src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png"/>](https://play.google.com/store/apps/details?id=com.cleo.myha)

&nbsp;

## Features

####  Create Habit
1. Users can create personal habit cards or group habit cards based on categories
2. Multiple habit cards can be created at the same time.

<img width="24%" src="images/habit3.png"/> <img width="24%" src="images/habit1.png"/> <img width="24%" src="images/habit2.png"/> 

&nbsp;
#### Join Community 
1. Users can enter the chat room after joining the group card；
2. Through the function of group chat and interaction, you can encourage teammates to form good habits and achieve same goals together.

<img width="24%" src="images/exp3.png"/> <img width="24%" src="images/habit4.png"/> 


&nbsp;
#### Create Post and add your comment
1. After completing the daily task, you can post the article；
2. On Discover Page, you can browse the posts of other users and interact with them by leaving messages for your opinon.

<img width="24%" src="images/home3.png"/> <img width="24%" src="images/exp1.png"/> <img width="24%" src="images/exp2.png"/>


&nbsp;
&nbsp;


## Technical Highlights 

* Structured by **MVVM** through **ViewModel** and **LiveData** to enhance
maintainability and readablity
* Used **RecyclerView** with **StaggeredGridLayoutManager** to
complete waterfall layout
* Applied **ViewPager** and **TabLayout** for screen slides on multiple
pages for better user experience
* Used **CenterZoomLayoutManager** to scale up the center item in
RecyclerView
* Uploaded images on **Firebase Storage** to save the image URLs on
cloud stroage
* Utilized **Applandeo Calendar** to highlight even task
* Used **Firestore SnapshotListener** to synchronize real-time chat
room with low-latency database
* Implemented **Google Login** to enable user quickly to sign-in
* Analyzed the completion of habits by using **Williamchart**
* Loaded images and cached images with **Glide** for displaying on UI
* Implemented **Lottie** and **Animator** for motion effect

&nbsp;
&nbsp;
## Implementation

* Design Pattern 
  <br> MVVM

* Jetpack
   <br>Navigation
   <br>LiveData
  
* User Interface
  <br> RecyclerView
  <br> ViewPager

* Tools
  <br> Firebase `Authentication` `Firestore` `Storage` `Crashlytics`
  <br> Glide

* Third Party Libraries
  <br>[Lottie](https://github.com/airbnb/lottie-android)
  <br>[Applandeo Calendar](https://github.com/Applandeo/Material-Calendar-View)
  <br>[Williamchart](https://github.com/diogobernardino/williamchart)
  

&nbsp;
&nbsp;
## Requirements

* Android SDK 26


## Contact
<p>Email: cv619115@gmail.com <br>
Linkedin: www.linkedin.com/in/cleo22
</P>