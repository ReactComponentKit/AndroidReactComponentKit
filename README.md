# AndroidReactComponentKit

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](http://opensource.org/licenses/MIT)

AndroidReactComponentKit is [ReactComponentKit](https://github.com/ReactComponentKit/ReactComponentKit) for Android. AndroidReactComponentKit is a library for building Activity or Fragment. You can make that based on Components. Also, It uses MVVM and Redux architectures for unidirectional data flow.

<div align="center"><img src="https://raw.githubusercontent.com/ReactComponentKit/AndroidReactComponentKit/master/art/AndroidReactComponentKit.png"></div>

## Data Flow

 * (1) : Components dispatch actions
 * (2) : ViewModel passes the actions to the store. You can use beforeDispatch(action:) method to do some work before dispatching actions.
 * (3) : Store passes current state to the Redux pipeline. Middlewares, Reducers and Postwares make a new state.
 * (4) : New State comes out from the Redux pipeline. It is passed to the store.
 * (5) : Store passes the new state to the ViewModel.
 * (6) : ViewModel passes or propagates the new state to the Components.

## Components

AndroidReactComponentKit provides four components basically. 

 * ViewComponent
 	* ViewComponent is a view. It is an abstraction of the view that makes up the screen as a component. You can use ViewComponent to create a layout of the Activity or Fragment. It can also be used to create an item views in RecyclerView.
 * FragmentComponent
 	* FragmentComponet is a fragment. You can use FragmentComponent when creating an Activity. If you delegate responsibility for an Activity to a FragmentComponent, you can avoid creating a massive Activity.
 * RecyclerViewAdapter
 	* The RecyclerViewAdapter connects ViewComponent and RecyclerView. The RecyclerViewAdapter can use several types of views, but only one LayoutManager can be used.
 * CollectionViewAdapter
 	* The CollectionViewAdapter connects ViewComponent and RecyclerView. Unlike the RecyclerViewAdapter, you can use multiple LayoutManagers. In other words, you can use various LayoutManagers with different types of views.  

## Examples

### [Hello Android RCK](https://github.com/ReactComponentKit/HelloAndroidRCK)

 ![](https://raw.githubusercontent.com/ReactComponentKit/HelloAndroidRCK/master/art/result.gif)

### [Hello Android RoomDB](https://github.com/ReactComponentKit/HelloRoom)

 ![](https://raw.githubusercontent.com/ReactComponentKit/HelloRoom/master/art/result.gif)

### [Counter](https://github.com/ReactComponentKit/AndroidReactComponentKit/tree/master/app/src/main/java/com/github/skyfe79/android/library/app/examples/counter2)

 ![](./art/counter.gif)
 
### [RecyclerView with DiffUtil](https://github.com/ReactComponentKit/AndroidReactComponentKit/tree/master/app/src/main/java/com/github/skyfe79/android/library/app/examples/emojicollection)
 
 ![](./art/emoji-diffutil.gif)
 
### [CollectionViewAdapter](https://github.com/ReactComponentKit/AndroidReactComponentKit/tree/master/app/src/main/java/com/github/skyfe79/android/library/app/examples/collectionview)

 ![](./art/collectionviewadapter.gif)
 
## Setup Gradle

### Project's build.gradle

```groovy
ext.kotlin_version = '1.3.31'
ext.anko_version='0.10.8'
ext.androidx_lifecycle_version='2.0.0'
ext.rxjava2_version='2.2.8'
ext.rxkotlin_version='2.3.0'
ext.rck_version='2.0.0'
```

### App's build.gradle

```groovy
dependencies {
	...
	implementation "io.reactivex.rxjava2:rxjava:$rxjava2_version"
	implementation "io.reactivex.rxjava2:rxkotlin:$rxkotlin_version"
	implementation "org.jetbrains.anko:anko:$anko_version"
	implementation "androidx.lifecycle:lifecycle-extensions:$androidx_lifecycle_version"
	implementation "com.github.skyfe79.android:reactcomponentkit:$rck_version"
}
```

## MIT License

The MIT License (MIT)

Copyright (c) 2016 Sungcheol Kim, [https://github.com/ReactComponentKit/AndroidReactComponentKit](https://github.com/ReactComponentKit/AndroidReactComponentKit)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


