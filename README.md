# Android and tbdex

This shows the tbdex SDK working in Android. 

This includes an `AndroidKeyManager` implementation that uses the Android Keystore to store that tbdex uses for DIDs and VCs.

Open this in Android Studio and run - it will show a verifiable credential, and if you connect it to a PFI will show a list of offerings. 

Android specific build tips:  

* exclude META-INF/DEPENDENCIES
* exclude other things like protobuf, bouncy castle etc (see gradle build files for info) so that it prefers android shipped versions.
* Check out the build.gradle.kts files for more info.

TODO: 
* Possibly migrate AndroidKeyManager to web5-kt
* Add an RFQ and order placement
* Package both web5-kt and tbdex-kt with an android option where the provided dependencies are already excluded to simplify usage.

![image](https://github.com/TBD54566975/tbdex-example-android/assets/14976/12b0efc5-4d1d-4629-8bf3-8ee490a4c9c0)
  
 
