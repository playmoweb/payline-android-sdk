package com.myluckyday.test.paylinesdk.payment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.app.domain.SdkAction
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import com.myluckyday.test.paylinesdk.app.util.IntentExtraDelegate
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkAction
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult
import com.myluckyday.test.paylinesdk.payment.presentation.PaymentActivity
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
                is PaymentSdkResult.DidGetBuyerShortCut -> listener?.didGetBuyerShortCut(sdkResult.buyer)
            }
        }
    }

    /**
     * S'inscris au broadcast qui permet de communiquer avec la webView
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
     */
    fun showPaymentForm(token: String, uri: Uri) {
        val c = context ?: return
        val intent = PaymentActivity.buildIntent(c, uri)
        val opts = ActivityOptionsCompat.makeCustomAnimation(c, 0, 0).toBundle()
        c.startActivity(intent, opts)
    }

    /**
     * Ferme la liste des moyens de paiement sans avoir effectué ou sélectionné un moyen paiement
     */
    fun cancelPaymentForm() {
        endToken(true)
    }

    /**
     * Mise à jour des informations de la session de paiement (adresses, montant,...) après l'initialisation du widget
     * et avant la finalisation du paiement.
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
     */
    fun endToken(handledByMerchant: Boolean) {
        broadcastAction(PaymentSdkAction.EndToken(handledByMerchant))
    }

    /**
     * Renvoie la clé du language du widget (passé dans la trame DoWebPayment)
     */
    fun getLanguage() {
        broadcastAction(PaymentSdkAction.GetLanguage())
    }

    /**
     * Renvoie une information du contexte grâce à sa clé
     */
    fun getContextInfo(key: String?) {
        broadcastAction(PaymentSdkAction.GetContextInfo(key))
    }

    /**
     * Déclenche la finalisation du paiement ShortCut (à appeler en context.state PAYMENT_TRANSITIONAL_SHORTCUT)
     */
    fun finalizeShortCut() {
        broadcastAction(PaymentSdkAction.FinalizeShortCut())
    }

    /**
     * Renvoie un objet complexe contenant les données de l'acheteur provenant du partenaire shortcut.
     */
    fun getBuyerShortCut() {
        broadcastAction(PaymentSdkAction.GetBuyerShortCut())
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
