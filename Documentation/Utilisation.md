# PaylineSDK

## Utilisation

### Initialisation

Pour l'initialisation du SDK, il faut tout d'abord instancier un  `PaymentController()` et un  `WalletController()` et ensuite les associer à leur listener qui seront décrits par la suite. Habituellement, cela est fait dans le  `onCreate()` de l'activité:

```kotlin
private var paymentController = PaymentController()
paymentController.registerListener(listener, context)

private var walletController = WalletController()
walletController.registerListener(listener, context)
```
La méthode d'initialisation du paiement requiert deux paramètres: un "paymentControllerListener" et le context
La méthode d'initialisation du porte-monnaie requiert deux paramètres: un "walletControllerListener" et le context

Cependant, il faut aussi dissocier le listener lorsque vous avez fini d'utiliser le SDK. Habituellement, cela est fait dans le  `onDestroy()` de l'activité:

```kotlin
paymentController.unregisterListener()
walletController.unregisterListener()
```

Pour que votre activité agisse comme un listener, vous devez implémenter les interfaces `PaymentControllerListener` et `WalletControllerListener`:

```kotlin
class MainActivity : AppCompatActivity(), PaymentControllerListener, WalletControllerListener
```

### Configuration

La méthode `showPaymentForm` est utilisée pour afficher la page des moyens de paiement.

```kotlin
private val paymentController = PaymentController()
paymentController.showPaymentForm(uri)
```

OR

La méthode `showManageWallet` est utilisée pour afficher la page du porte-monnaie.

```kotlin
private val walletController = WalletController()
walletController.manageWebWallet(uri)
```
Ces deux méthodes requièrent l'uri de la page vers laquelle nous devons être redirigés. La récupération du paramètre `uri` se fera selon vos choix d'implementation. 
Pour plus d'informations, veuillez vous référer à la documentation Payline en cliquant [ici](https://support.payline.com/hc/fr/articles/360000844007-PW-Int%C3%A9gration-Widget)

### PaymentController

Une fois que la page des moyens de paiement a été affichée, plusieurs méthodes sont accessibles:

```kotlin
fun updateWebPaymentData(data: JSONObject)
```
`updateWebPaymentData` permet de mettre à jour les informations de session de paiement après l'initialisation du widget et avant la finalisation du paiement.


```kotlin
fun getIsSandbox()
```
`getIsSandbox` permet de savoir si l'environnement est une production ou une homologation.


```kotlin
fun endToken(handledByMerchant: Boolean, additionalData: JSONObject?)
```
`endToken` permet de mettre fin à la vie du jeton de session web.


```kotlin
fun getLanguage()
```
`getLanguage` permet de connaitre la clé du language du widget.


```kotlin
fun getContextInfo(key: ContextInfoKey)
```
`getContextInfo` permet de connaitre l'information dont la clé est passée en paramètre.
Les différentes clés disponibles pour cette fonction sont les suivantes:

    - AMOUNT_SMALLEST_UNIT("PaylineAmountSmallestUnit")
    - CURRENCY_DIGITS("PaylineCurrencyDigits")
    - CURRENCY_CODE("PaylineCurrencyCode")
    - BUYER_FIRST_NAME("PaylineBuyerFirstName")
    - BUYER_LAST_NAME("PaylineBuyerLastName")
    - SHIPPING_ADDRESS_STREET2("PaylineBuyerShippingAddress.street2")
    - SHIPPING_ADDRESS_COUNTRY("PaylineBuyerShippingAddress.country")
    - SHIPPING_ADDRESS_NAME("PaylineBuyerShippingAddress.name")
    - SHIPPING_ADDRESS_STREET1("PaylineBuyerShippingAddress.street1")
    - SHIPPING_ADDRESS_CITYNAME("PaylineBuyerShippingAddress.cityName")
    - SHIPPING_ADDRESS_ZIPCODE("PaylineBuyerShippingAddress.zipCode")
    - BUYER_MOBILE_PHONE("PaylineBuyerMobilePhone")
    - SHIPPING_ADDRESS_PHONE("PaylineBuyerShippingAddress.phone")
    - BUYER_IP("PaylineBuyerIp")
    - FORMATTED_AMOUNT("PaylineFormattedAmount")
    - ORDER_DATE("PaylineOrderDate")
    - ORDER_REF("PaylineOrderRef")
    - ORDER_DELIVERY_MODE("PaylineOrderDeliveryMode")
    - ORDER_DELIVERY_TIME("PaylineOrderDeliveryTime")
    - ORDER_DETAILS("PaylineOrderDetails")


### PaymentControllerListener

Le `PaymentControllerListener` est une interface qui définit la communication entre l'application et le PaymentController. Il va permettre d'avertir la classe qui l'implémente lorsque des données ou des actions sont reçues. Il contient différentes méthodes:

```kotlin
fun didShowPaymentForm()
```
`didShowPaymentForm` est la méthode appelée lorsque la liste des moyens de paiement a été affichée.


```kotlin
fun didFinishPaymentForm(state: WidgetState)
```
`didFinishPaymentForm` est la méthode appelée lorsque le paiement a été terminé. Elle reçoit en paramètre un objet de type  `widgetState` qui correspond aux différents états possible lors de la fin du paiement. Cet objet peut prendre les valeurs suivantes:

    - PAYMENT_METHODS_LIST
    - PAYMENT_CANCELED
    - PAYMENT_SUCCESS
    - PAYMENT_FAILURE
    - PAYMENT_FAILURE_WITH_RETRY
    - TOKEN_EXPIRED
    - BROWSER_NOT_SUPPORTED
    - PAYMENT_METHOD_NEEDS_MORE_INFOS
    - PAYMENT_REDIRECT_NO_RESPONSE
    - MANAGE_WEB_WALLET
    - ACTIVE_WAITING
    - PAYMENT_CANCELED_WITH_RETRY
    - PAYMENT_ONHOLD_PARTNER
    - PAYMENT_SUCCESS_FORCE_TICKET_DISPLAY


```kotlin
fun didGetIsSandbox(isSandbox: Boolean)
```
`didGetIsSandbox` est la méthode appelée lorsque l'environnement a été reçu. `isSandbox`  vaudra true si l'environnement est une production et false si c'est une homologation.


```kotlin
fun didGetLanguage(language: String)
```
`didGetLanguage` est la méthode appelée lorsque la clé du language du widget est connue. `language` peut avoir des valeurs comme "fr", "en" ...


```kotlin
fun didGetContextInfo(key: ContextInfoKey)
```
`didGetContextInfo` est la méthode appelée lorsque l'information du contexte est connue.

### WalletControllerListener

Le `WalletControllerListener` est une interface qui définit la communication entre l'application et le WalletController. Il va permettre d'avertir la classe qui l'implémente lorsque des données ou des actions sont reçues. Il contient une méthode:

```kotlin
fun didShowManageWebWallet()
```
`didShowManageWebWallet` est la méthode appelée lorsque le porte-monnaie a été affiché.

## Example Usage

### Initialisation du moyen de paiement

```kotlin
class TestApp: AppCompatActivity(), PaymentControllerListener {

    private lateinit var paymentController: PaymentController

    private var paymentUri: Uri? = null
    
    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        result?.redirectUrl?.let {
            paymentUri = Uri.parse(it)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        paymentController = PaymentController()
        paymentController.registerListener(this, this)
        
        paymentTokenButton.setOnClickListener { fetchTokenForPayment() }
        
        paymentButton.setOnClickListener {
            paymentUri ?: return@setOnClickListener
            //On appelle la méthode showPaymentForm avec l'url du tunnel de paiement récupéré en fonction de votre implémentation
            paymentController.showPaymentForm(paymentUri!!)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        paymentController.unregisterListener()
    }
    
    private fun fetchTokenForPayment() {
        TokenFetcher(fetchTokenCallback).execute(FetchTokenParams(...))
    }
    
    override fun didShowPaymentForm() {
        //Gérer l'action ici
    }
    
    override fun didFinishPaymentForm(state: WidgetState) {
        //Gérer l'action ici
    }
    
    override fun didGetIsSandbox(isSandbox: Boolean) {
        //Gérer l'action ici
    }
    
    override fun didGetLanguage(language: String) {
        //Gérer l'action ici
    }
    
    override fun didGetContextInfo(info: ContextInfoResult) {
        //Gérer l'action ici
    }

}
```

### Initialisation du porte-monnaie

```kotlin
class TestApp: AppCompatActivity(), WalletControllerListener {

    private lateinit var walletController: WalletController

    private var walletUri: Uri? = null

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        result?.redirectUrl?.let {
            walletUri = Uri.parse(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        walletController = WalletController()
        walletController.registerListener(this, this)

        walletTokenButton.setOnClickListener { fetchTokenForWallet() }

        walletButton.setOnClickListener {
            walletUri ?: return@setOnClickListener
            //On appelle la méthode manageWebWallet avec l'url du wallet récupéré en fonction de votre implémentation.
            walletController.manageWebWallet(walletUri!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        walletController.unregisterListener()
    }

    private fun fetchTokenForWallet() {
        TokenFetcher(fetchTokenCallback).execute(FetchTokenParams(...))
    }

    override fun didShowManageWebWallet() {
        //Gérer l'action ici
    }

}
```

