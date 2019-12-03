# PaylineSDK

## Description

Le SDK Payline est un kit de développement qui va permettre d'intéragir avec le service Payline afin d'effectuer un paiement ou de voir le portefeuille.

## Installation

Le SDK est disponible sur Maven Central. Pour l’installer :

1. Vérifier que vous avez ajouté le repository Maven Central à votre projet
    ```groovy
    repositories {
       mavenCentral()
    }
    ```
1. Ajouter la dépendance suivante :
    ```groovy
    dependencies {
        implementation ‘com.payline:android-sdk:0.9.0’
    }
    ```

# Utilisation

### Initialisation

Pour l'initialisation du SDK, il faut tout d'abord instancier un  `PaymentController()` et un  `WalletController()` et ensuite les associer à leur listener qui seront décrits par la suite. Habituellement, cela est fait dans le  `onCreate()` de l'activité :

```kotlin
private var paymentController = PaymentController()
paymentController.registerListener(listener, context)

private var walletController = WalletController()
walletController.registerListener(listener, context)
```
La méthode d'initialisation du paiement requiert deux paramètres : un "paymentControllerListener" et le "context".
La méthode d'initialisation du portefeuille requiert deux paramètres : un "walletControllerListener" et le "context".

Cependant, il faut aussi dissocier le listener lorsque vous avez fini d'utiliser le SDK. Habituellement, cela est fait dans le  `onDestroy()` de l'activité :

```kotlin
paymentController.unregisterListener()
walletController.unregisterListener()
```

Pour que votre activité agisse comme un listener, vous devez implémenter les interfaces `PaymentControllerListener` et `WalletControllerListener` :

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

La méthode `showManageWallet` est utilisée pour afficher la page de gestion du portefeuille.

```kotlin
private val walletController = WalletController()
walletController.manageWebWallet(uri)
```
Ces deux méthodes requièrent l'uri de la page vers laquelle nous devons être redirigés. La récupération du paramètre `uri` se fera selon vos choix d'implementation.
Pour plus d'informations, veuillez vous référer à la documentation Payline en cliquant [ici](https://support.payline.com/hc/fr/articles/360000844007-PW-Int%C3%A9gration-Widget)


# Payline Documentation

La documentation de Payline peut être trouvé [here](https://support.payline.com/hc/fr/articles/360000844007-PW-Int%C3%A9gration-Widget). Elle offre une vue d'ensemble du sytème, des détails et des explications sur certains sujets.

---

## Author

Payline, support@payline.com

## License

PaylineSDK is available under the MIT license. See the LICENSE file for more info.
