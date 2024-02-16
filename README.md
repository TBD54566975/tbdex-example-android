# Android and tbdex

This shows the tbdex SDK working in Android in a simple mobile app.

Also includes an `AndroidKeyManager` implementation that uses the Android Keystore to store that tbdex uses for DIDs and VCs.

## Running: 

Open this in Android Studio and run it - it will show a verifiable credential, and if you connect it to a PFI will show a list of offerings. 

## Android specific build tips  

* exclude META-INF/DEPENDENCIES
* exclude other things like protobuf, bouncy castle etc so that it uses android shipped versions.
* Check out the `build.gradle.kts` file for more info.

TODO: 
* Possibly migrate AndroidKeyManager to web5-kt
* Add an RFQ and order placement
* Package both web5-kt and tbdex-kt with an android option where the provided dependencies are already excluded to simplify usage.

  
 ![Screenshot 2024-02-16 at 6 35 27 pm](https://github.com/TBD54566975/tbdex-example-android/assets/14976/b6071316-60ad-4f87-bb7e-c0e525c41562)

