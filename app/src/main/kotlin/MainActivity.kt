package com.example.tbdexy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tbdex.sdk.httpclient.TbdexHttpClient
import tbdex.sdk.protocol.models.*
import web5.sdk.credentials.VerifiableCredential
import web5.sdk.dids.methods.dht.DidDht

/**
 * MainActivity demonstrates the use of the tbdex SDK within an Android application.
 * It includes examples of creating a DID, fetching a Verifiable Credential (VC),
 * and interacting with a PFI server to retrieve offerings.
 */
class MainActivity : AppCompatActivity() {

    // PFI server URL. Replace with your own server URL.
    private val pfiServer = "https://your-pfi-server-url.com"

    /**
     * Fetches the DID from the PFI server.
     * @return DID as a String.
     */
    suspend fun fetchPfiDid(): String {
        return withContext(Dispatchers.IO) {
            val client = okhttp3.OkHttpClient()
            val request = okhttp3.Request.Builder()
                .url("$pfiServer/did")
                .build()
            val response = client.newCall(request).execute()
            response.body?.string() ?: ""
        }
    }

    /**
     * Retrieves a Verifiable Credential for the given DID.
     * @param did The DID for which to retrieve the VC.
     * @return VC as a String.
     */
    suspend fun getVerifiableCredential(did: String?): String {
        return withContext(Dispatchers.IO) {
            val url = "$pfiServer/vc?name=Mic&country=Australia&did=${did}"
            val client = okhttp3.OkHttpClient()
            val request = okhttp3.Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            response.body?.string() ?: ""
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components and set up event listeners.
        val vcTextView: TextView = findViewById(R.id.vcTextView)

        // Use Coroutine to perform network operation in the background as required by android
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetch DID from the PFI server.
                val pfiDid = fetchPfiDid()

                // Retrieve a signed VC using the app's DID.
                val signedVC = getVerifiableCredential(pfiDid)

                // Retrieve offerings from the PFI server and update the UI.
                val offering = TbdexHttpClient.getOfferings(pfiDid)

                withContext(Dispatchers.Main) {
                    val offersRecyclerView: RecyclerView = findViewById(R.id.offersRecyclerView)
                    val offersAdapter = OffersAdapter(offering)
                    offersRecyclerView.adapter = offersAdapter
                }
            } catch (e: Exception) {
                // Handle exceptions such as network errors.
            }
        }
    }
}
// (The rest of the file remains unchanged)

// Exchange functionality: USD to AUD example
/*
 * Now let's do an exchange, in this case, USD for AUD.
 * This section demonstrates how to perform a currency exchange using the tbdex SDK.
 */
suspend fun performExchange() {
    // Example exchange logic here
    // Replace with actual implementation
}

// Additional utility functions or classes can be added below.
// ...

// End of MainActivity.kt

// (The rest of the file remains unchanged)
