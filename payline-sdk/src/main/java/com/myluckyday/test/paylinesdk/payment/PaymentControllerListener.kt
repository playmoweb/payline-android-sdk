package com.myluckyday.test.paylinesdk.payment

import com.myluckyday.test.paylinesdk.app.util.ParcelableJsonElement
import org.json.JSONArray
import org.json.JSONObject

interface PaymentControllerListener {

    /**
     * Appelée lorsque la liste des moyens de paiement a été affiché
     */
    fun didShowPaymentForm()

    /**
     * Appelée lorsque la liste des moyens de paiement a été fermée sans avoir effectué ou sélectionné un moyen paiement
     */
    fun didCancelPaymentForm()

    /**
     * TODO: doc
     */
    fun didFinishPaymentForm()

    /**
     * Appelée lorsque l'environnement est connu
     */
    fun didGetIsSandbox(isSandbox: Boolean)

    /**
     * Appelée lorsque la clé du language du widget est connue
     */
    fun didGetLanguage(language: String)

    /**
     * Appelée lorsque l'information du contexte est connue
     */
    fun didGetContextInfo(info: ParcelableJsonElement)

    /**
     * Appelée lorsque l'objet contenant les données de l'acheteur a été récupéré
     */
    fun didGetBuyerShortCut(buyer: JSONObject)

}
