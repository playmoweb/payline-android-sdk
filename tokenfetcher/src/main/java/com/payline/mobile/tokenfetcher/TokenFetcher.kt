package com.payline.mobile.tokenfetcher

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
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

            val json = params.data

            val uri = Uri.Builder()
                .scheme("https")
                .authority("demo-sdk-merchant-server.ext.dev.payline.com")
                .apply {
                    when(params.type) {
                        FetchTokenParams.Type.PAYMENT -> path("init-web-pay")
                        FetchTokenParams.Type.WALLET -> path("init-manage-wallet")
                    }
                }
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
                type = params.type,
                code = jsonResponse.getString("code"),
                message = jsonResponse.getString("message"),
                redirectUrl = jsonResponse.getString("redirectUrl"),
                token = jsonResponse.getString("token")
            )

        } catch(t: Throwable) {
            Log.e("TokenFetcher", "error getting token", t)
            t.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: FetchTokenResult?) {
        callbackRef.get()?.invoke(result)
    }

}