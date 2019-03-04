package com.myluckyday.test.paylinesdk.payment

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
    fun didGetContextInfo(info: String)

}
