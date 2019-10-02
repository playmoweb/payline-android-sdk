package com.payline.mobile.androidsdk.payment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.androidsdk.core.data.ContextInfoKey
import com.payline.mobile.androidsdk.core.domain.SdkAction
import com.payline.mobile.androidsdk.core.domain.SdkResult
import com.payline.mobile.androidsdk.payment.domain.PaymentSdkAction
import com.payline.mobile.androidsdk.payment.domain.PaymentSdkResult
import com.payline.mobile.androidsdk.payment.presentation.PaymentActivity
import org.json.JSONObject

/**
 * Interface pour les opérations de paiement
 */
class PaymentController {

    private var listener: PaymentControllerListener? = null
    private var context: Context? = null

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val sdkResult = intent.getParcelableExtra<PaymentSdkResult>(SdkResult.EXTRA_SDK_RESULT)
            when(sdkResult) {
                is PaymentSdkResult.DidShowPaymentForm -> listener?.didShowPaymentForm()
                is PaymentSdkResult.DidFinishPaymentForm -> listener?.didFinishPaymentForm(sdkResult.state)
                is PaymentSdkResult.DidGetLanguageCode -> listener?.didGetLanguageCode(sdkResult.language)
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
     * @param uri uri qui redirige la webView vers la liste des moyens de paiement
     */
    fun showPaymentForm(uri: Uri) {
        val c = context ?: return
        PaymentActivity.buildIntent(c, uri).apply {
            c.startActivity(this)
        }
    }

    /**
     * Mise à jour des informations de la session de paiement (adresses, montant,...) après l'initialisation du widget
     * et avant la finalisation du paiement.
     *
     * @param data données qui correspondent aux informations de paiement, de la commande et de l'acheteur
     * @link <https://support.payline.com/hc/fr/articles/360017949833-PW-API-JavaScript>
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

    /**
     * Met fin à la vie du jeton de session web
     *
     * @param handledByMerchant handleByMerchant est à true si l'action de supprimer le jeton de session web vient du marchand
     * @param additionalData
     *
     */
    fun endToken(handledByMerchant: Boolean, additionalData: String?) {
        broadcastAction(PaymentSdkAction.EndToken(handledByMerchant, additionalData))
    }

    /**
     * Renvoie la clé du language du widget (passé dans la trame DoWebPayment)
     */
    fun getLanguageCode() {
        broadcastAction(PaymentSdkAction.GetLanguageCode())
    }

    /**
     * Renvoie une information du contexte grâce à sa clé
     *
     * @param key [ContextInfoKey] correspond à la clé de la donnée que l'on veut récupérer
     * @link <https://support.payline.com/hc/fr/articles/360017949833-PW-API-JavaScript>
     */
    fun getContextInfo(key: ContextInfoKey) {
        broadcastAction(PaymentSdkAction.GetContextInfo(key))
    }

    private val bman: LocalBroadcastManager?
        get() {
            return context?.let { LocalBroadcastManager.getInstance(it) }
        }

    private fun broadcastAction(action: PaymentSdkAction) {
        bman?.sendBroadcast(Intent(SdkAction.BROADCAST_SDK_ACTION).apply {
            putExtra(SdkAction.EXTRA_SDK_ACTION, action)
        })
    }

}
