# LBO - My book

## App Features
- User can search on Google book API by author and title
- user can see the search result
- user can like book
- user can see liked book offline

## Setup
1. In your terminal : Clone this repo
2. Initialises submodules with :
```sh
git submodule update --init --recursive
```
3. Open the project in Android Studio
4. Do a gradle sync
5. Now you should be able to build and deploy
6. Enjoy <3

## Submodules
This project use the MVI (Model View Intent) architecture and use a custom mvi library : 
[https://github.com/Illiouchine/mvi-library](https://github.com/Illiouchine/mvi-library)

## Used Libraries
- Mvi architecture with [mvi-library](https://github.com/Illiouchine/mvi-library)
- Ui Rendering with [compose](https://developer.android.com/jetpack/compose)
- Ui Design with [compose-material3](https://developer.android.com/jetpack/androidx/releases/compose-material3?hl=en)
- Data Storage with [room](https://developer.android.com/training/data-storage/room)
- Dependencies injection with [hilt-android](https://developer.android.com/training/dependency-injection/hilt-android)
- Testing with [mockito](https://developer.android.com/training/testing/local-tests)
- Asynchronous with [Coroutine and Flow](https://developer.android.com/kotlin/flow)
- 