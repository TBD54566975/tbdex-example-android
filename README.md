# Example android

This shows the tbdex SDK working in Android. 

This includes an `AndroidKeyManager` implementation that uses the Android Keystore to store that tbdex uses for DIDs and VCs.

Open this in Android Studio and run - will show a Verifiable Credential. 

Tricks: 

* exclude META-INF/DEPENDENCIES
* exclude other things like protobuf, bouncy castle etc (see gradle build files for info) so that it prefers android shipped versions.



![image](https://github.com/TBD54566975/tbdex-example-android/assets/14976/12b0efc5-4d1d-4629-8bf3-8ee490a4c9c0)
  
 
