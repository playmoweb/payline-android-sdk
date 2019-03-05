package com.myluckyday.test.paylinesdk.payment

import com.myluckyday.test.paylinesdk.core.data.ContextInfoResult

interface PaymentControllerListener {

    /**
     * Appelée lorsque la liste des moyens de paiement a été affichée
     */
    fun didShowPaymentForm()

    /**
     * Appelée lorsque la liste des moyens de paiement a été fermée sans avoir effectué ou sélectionné un moyen paiement
     */
    fun didCancelPaymentForm()

    /**
     * Appelée lorsque le paiement a été terminé
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
    fun didGetContextInfo(info: ContextInfoResult)

}
