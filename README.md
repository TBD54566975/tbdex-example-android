# Android and tbdex

This shows the tbdex SDK working in Android in a simple mobile app with Kotlin.
See `MainActivity.kt` for the main code showing tbdex APIs.
Also includes an `AndroidKeyManager` implementation that uses the Android Keystore to store that tbdex uses for DIDs and VCs.

This can serve as a starter app for tbdex on android. I think I have said android enough times now.

## Running 

* Open this in Android Studio and run it with a emulator, it generates and shows a DID and will print out a verifiable credential.

To run with a PFI to test it end to end:

* Run a local tunnel to the tbdex server: `cloudflared tunnel --url http://localhost:9000`.
* Take note of the public https url provided in step 1, and set it to `HOST` environment variable.
* Take that same url and set it as the pfiServer in `MainActivity.kt` : `private val pfiServer = host`
* Follow instructions to start this PFI: https://github.com/TBD54566975/example-pfi-aud-usd-tbdex
* Run the app and you will see an offer loaded from the PFI, and then it will place an order. 
* Check the logcat logs to see the result of the tbdex workflow including order status and completion.

## Android specific build tips  

* exclude META-INF/DEPENDENCIES
* exclude other things like protobuf, bouncy castle etc so that it uses android shipped versions.
* Check out the `build.gradle.kts` file for more info.

TODO: 

* ✅ Add an RFQ and order placement
* Migrate AndroidKeyManager to web5-kt
* Package both web5-kt and tbdex-kt with an android option where the provided dependencies are already excluded to simplify usage.

  
 ![Screenshot 2024-02-16 at 6 35 27 pm](https://github.com/TBD54566975/tbdex-example-android/assets/14976/b6071316-60ad-4f87-bb7e-c0e525c41562)

