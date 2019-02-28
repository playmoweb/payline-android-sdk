package com.myluckyday.test.payline_android.domain

import android.net.Uri
import android.os.AsyncTask
import com.myluckyday.test.payline_android.data.FetchTokenParams
import com.myluckyday.test.payline_android.data.FetchTokenResult
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class TokenFetcher(callback: (FetchTokenResult?)->Unit): AsyncTask<FetchTokenParams, Void, FetchTokenResult?>() {

    private val callbackRef = WeakReference(callback)

    override fun doInBackground(vararg p: FetchTokenParams): FetchTokenResult? {

        val params = p.firstOrNull() ?: return null

        try {

            val json = JSONObject()
            json.put("orderRef", params.orderRef)
            json.put("amount", params.amount)
            json.put("currencyCode", params.currencyCode)

            val uri = Uri.Builder()
                .scheme("https")
                .authority("demo-sdk-merchant-server.ext.dev.payline.com")
                .path("init-web-pay")
                .build()

            val conn = URL(uri.toString()).openConnection() as HttpURLConnection
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true

            val utf8 = Charset.forName("UTF-8")

            BufferedOutputStream(conn.outputStream).use {
                it.write(json.toString().toByteArray(utf8))
            }

            val responseString = conn.inputStream.bufferedReader(utf8).readText()

            conn.disconnect()

            val jsonResponse = JSONObject(responseString)

            return FetchTokenResult(
                code = jsonResponse.getString("code"),
                message = jsonResponse.getString("message"),
                redirectUrl = jsonResponse.getString("redirectUrl"),
                token = jsonResponse.getString("token")
            )

        } catch(t: Throwable) {
            t.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: FetchTokenResult?) {
        callbackRef.get()?.invoke(result)
    }

}