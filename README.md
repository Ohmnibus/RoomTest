Room Test App
=============

This is a simple app I wrote to test the following Architecture Components:

* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [ViewModels](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html)
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)

This sample all also support query filtering as the user enter search text.

Filtering is made by creating a new filtered `PagedList` that replace the previous
as the query filter changes.

Switching `PagedList`s are contained by a `MediatorLiveData`.
