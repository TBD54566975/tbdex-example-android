package com.example.tbdexy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyProtection
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
import tbdex.sdk.protocol.models.Offering
import tbdex.sdk.protocol.models.Rfq
import tbdex.sdk.protocol.models.RfqData
import web5.sdk.credentials.VerifiableCredential
import web5.sdk.crypto.InMemoryKeyManager
import web5.sdk.dids.Did
import web5.sdk.dids.methods.dht.DidDht
import java.security.KeyStore
import java.security.KeyStore.Entry
import java.security.KeyStore.PasswordProtection
import java.security.KeyStore.SecretKeyEntry
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Shows the basics of interacting with the the tbdex SDK in an android app.
 */
class MainActivity : AppCompatActivity() {

    private val pfiServer = "https://earl-researchers-quotations-ebook.trycloudflare.com"

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

    suspend fun getVerifiableCredential(did: Did): String {
        return withContext(Dispatchers.IO) {
            val url = "$pfiServer/vc?name=Mic&country=Australia&did=${did.uri}"
            val client = okhttp3.OkHttpClient()
            val request = okhttp3.Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            response.body?.string() ?: ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * This is a JWT that represents a Verifiable Credential (VC) prepared earlier
         */
        val signedVcJwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFZERTQSIsImtpZCI6ImRpZDpkaHQ6a2ZkdGJjbTl6Z29jZjVtYXRmOWZ4dG5uZmZoaHp4YzdtZ2J3cjRrM3gzcXppYXVjcHA0eSMwIn0.eyJ2YyI6eyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy92MSJdLCJ0eXBlIjpbIlZlcmlmaWFibGVDcmVkZW50aWFsIiwiRW1wbG95bWVudENyZWRlbnRpYWwiXSwiaWQiOiJ1cm46dXVpZDo4ZmQ1MjAzMC0xY2FmLTQ5NzgtYTM1ZC1kNDE3ZWI4ZTAwYjIiLCJpc3N1ZXIiOiJkaWQ6ZGh0OmtmZHRiY205emdvY2Y1bWF0ZjlmeHRubmZmaGh6eGM3bWdid3I0azN4M3F6aWF1Y3BwNHkiLCJpc3N1YW5jZURhdGUiOiIyMDIzLTEyLTIxVDE3OjAyOjAxWiIsImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImlkIjoiZGlkOmRodDp5MzltNDhvem9ldGU3ejZmemFhbmdjb3M4N2ZodWgxZHppN2Y3andiamZ0N290c2toOXRvIiwicG9zaXRpb24iOiJTb2Z0d2FyZSBEZXZlbG9wZXIiLCJzdGFydERhdGUiOiIyMDIxLTA0LTAxVDEyOjM0OjU2WiIsImVtcGxveW1lbnRTdGF0dXMiOiJDb250cmFjdG9yIn0sImV4cGlyYXRpb25EYXRlIjoiMjAyMi0wOS0zMFQxMjozNDo1NloifSwiaXNzIjoiZGlkOmRodDprZmR0YmNtOXpnb2NmNW1hdGY5Znh0bm5mZmhoenhjN21nYndyNGszeDNxemlhdWNwcDR5Iiwic3ViIjoiZGlkOmRodDp5MzltNDhvem9ldGU3ejZmemFhbmdjb3M4N2ZodWgxZHppN2Y3andiamZ0N290c2toOXRvIn0.ntcgPOdXOatULWo-q6gkuhKmi5X3bzCONQY38t_rsC1hVhvvdAtmiz-ccoLIYUkjECRHIxO_UZbOKgn0EETBCA"
        val vc = VerifiableCredential.parseJwt(signedVcJwt)
        print(vc) // VerifiableCredential details
        print(vc.issuer) // VerifiableCredential issuer

        val vcTextView: TextView = findViewById(R.id.vcTextView)

        /*
         * Create a new did for this app, and store it in the AndroidKeyManager (encrypted prefs).
         * This should be done once, and the did should be stored for future use automatically.
         */
        val keyManager = AndroidKeyManager(applicationContext)
        val did = DidDht.create(keyManager)

        vcTextView.text = "this is my did: " + did.didDocument?.id



        // Use Coroutine to perform network operation in the background as required by android
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val pfiDid = fetchPfiDid()
                print(pfiDid)



                val signedVC = getVerifiableCredential(did)
                print(signedVC)

                /*
                 * This will talk to a PFI (liquidity node) and get the offerings available. The DID that is provided is from the PFI server.
                 * This is a list of offerings which we can render later on. See the OffersAdapter class for how it shows some of the offering fields.
                 */
                val off = TbdexHttpClient.getOfferings(pfiDid)

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    val offersRecyclerView: RecyclerView = findViewById(R.id.offersRecyclerView)
                    val offersAdapter = OffersAdapter(off)
                    offersRecyclerView.adapter = offersAdapter
                }


            } catch (e: Exception) {
                e.printStackTrace()

                withContext(Dispatchers.Main) {
                    vcTextView.text = vcTextView.text.toString() + "\nUnable to load the offers, check logcat " + e.toString()
                }

            }
        }





    }
}

class OffersAdapter(private val offers: List<Offering>) : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.bind(offer)
    }

    override fun getItemCount(): Int = offers.size

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val exchangeRateTextView: TextView = itemView.findViewById(R.id.exchangeRateTextView)
        private val currenciesTextView: TextView = itemView.findViewById(R.id.currenciesTextView)

        fun bind(offer: Offering) {
            descriptionTextView.text = offer.data.description
            exchangeRateTextView.text = "Exchange Rate: ${offer.data.payoutUnitsPerPayinUnit}"
            currenciesTextView.text = "Payout: ${offer.data.payoutCurrency.currencyCode}, Payin: ${offer.data.payinCurrency.currencyCode}"
        }
    }
}

