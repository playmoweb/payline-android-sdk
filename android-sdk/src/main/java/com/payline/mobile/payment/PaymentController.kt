package com.payline.mobile.payment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.core.data.ContextInfoKey
import com.payline.mobile.core.domain.SdkAction
import com.payline.mobile.core.domain.SdkResult
import com.payline.mobile.payment.domain.PaymentSdkAction
import com.payline.mobile.payment.domain.PaymentSdkResult
import com.payline.mobile.payment.presentation.PaymentActivity
import org.json.JSONObject

class PaymentController {

    private var listener: PaymentControllerListener? = null
    private var context: Context? = null

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val sdkResult = intent.getParcelableExtra<PaymentSdkResult>(SdkResult.EXTRA_SDK_RESULT)
            when(sdkResult) {
                is PaymentSdkResult.DidShowPaymentForm -> listener?.didShowPaymentForm()
                is PaymentSdkResult.DidCancelPaymentForm -> listener?.didCancelPaymentForm()
                is PaymentSdkResult.DidFinishPaymentForm -> listener?.didFinishPaymentForm()
                is PaymentSdkResult.DidGetLanguage -> listener?.didGetLanguage(sdkResult.language)
                is PaymentSdkResult.DidGetIsSandbox -> listener?.didGetIsSandbox(sdkResult.isSandbox)
                is PaymentSdkResult.DidGetContextInfo -> listener?.didGetContextInfo(sdkResult.result)
            }
        }
    }

    /**
     * S'inscris au broadcast qui permet de communiquer avec la webView
     *
     * @param listener [PaymentControllerListener] utilisé pour la réception du résultat de la webView
     * @param context context utilisé à la création de PaymentController
     */
    fun registerListener(listener: PaymentControllerListener, context: Context) {
        this.listener = listener
        this.context = context
        bman?.registerReceiver(broadcastReceiver, IntentFilter(SdkResult.BROADCAST_SDK_RESULT))
    }

    /**
     * Se désinscris du broadcast qui permet de communiquer avec la webView
     */
    fun unregisterListener() {
        bman?.unregisterReceiver(broadcastReceiver)
        this.listener = null
        this.context = null
    }

    /**
     * Affiche la liste des moyens de paiement
     *
     * @param token
     * @param uri uri qui redirige la webView vers la liste des moyens de paiement
     */
    fun showPaymentForm(token: String, uri: Uri) {
        val c = context ?: return
        val intent = PaymentActivity.buildIntent(c, uri)
        val opts = ActivityOptionsCompat.makeCustomAnimation(c, 0, 0).toBundle()
        c.startActivity(intent, opts)
    }

    /**
     * Mise à jour des informations de la session de paiement (adresses, montant,...) après l'initialisation du widget
     * et avant la finalisation du paiement.
     *
     * @param data données qui correspondent aux informations de paiement, de la commande et de l'acheteur
     * @see <https://payline.atlassian.net/wiki/spaces/DT/pages/1329037328/PW+-+API+JavaScript>
     */
    fun updateWebPaymentData(data: JSONObject) {
        broadcastAction(PaymentSdkAction.UpdateWebPaymentData(data))
    }

    /**
     * Permet de connaitre l’environnement : production ou homologation. La fonction retourne true ou false.
     */
    fun getIsSandbox() {
        broadcastAction(PaymentSdkAction.IsSandbox())
    }

    //TODO Demander à quoi correspond le additionalData
    /**
     * Met fin à la vie du jeton de session web
     *
     * @param handledByMerchant handleByMerchant est à true si l'action de supprimer le jeton de session web vient du marchand
     * @param additionalData
     *
     */
    fun endToken(handledByMerchant: Boolean, additionalData: JSONObject?) {
        broadcastAction(PaymentSdkAction.EndToken(handledByMerchant, additionalData))
    }

    /**
     * Renvoie la clé du language du widget (passé dans la trame DoWebPayment)
     */
    fun getLanguage() {
        broadcastAction(PaymentSdkAction.GetLanguage())
    }

    /**
     * Renvoie une information du contexte grâce à sa clé
     *
     * @param key [ContextInfoKey] correspond à la clé de la donnée que l'on veut récupérer
     * @see <https://payline.atlassian.net/wiki/spaces/DT/pages/1329037328/PW+-+API+JavaScript>
     */
    fun getContextInfo(key: ContextInfoKey) {
        broadcastAction(PaymentSdkAction.GetContextInfo(key))
    }

    private val bman: LocalBroadcastManager?
        get() {
            val c = context ?: return null
            return LocalBroadcastManager.getInstance(c)
        }

    private fun broadcastAction(action: PaymentSdkAction) {
        bman?.sendBroadcast(Intent(SdkAction.BROADCAST_SDK_ACTION).apply {
            putExtra(SdkAction.EXTRA_SDK_ACTION, action)
        })
    }

}
