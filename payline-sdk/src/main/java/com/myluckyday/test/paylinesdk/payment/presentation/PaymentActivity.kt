package com.myluckyday.test.paylinesdk.payment.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.myluckyday.test.paylinesdk.R
import com.myluckyday.test.paylinesdk.app.presentation.WebFragment
import com.myluckyday.test.paylinesdk.app.presentation.WebViewModel
import kotlinx.android.synthetic.main.activity_payment.*

internal class PaymentActivity: AppCompatActivity() {

    private data class TypedParams(val uri: Uri)

    companion object {

        private const val EXTRA_URI = "EXTRA_URI"
        private const val BOTTOM_SHEET_ANIM_DURATION = 150

        fun buildIntent(context: Context, uri: Uri): Intent {
            return Intent(context, PaymentActivity::class.java).apply {
                putExtra(EXTRA_URI, uri)
            }
        }

        private fun Intent.getTypedParams(): TypedParams {
            val uri: Uri = getParcelableExtra(EXTRA_URI)
            return TypedParams(uri)
        }

    }

    private val bottomSheetBehavior: BottomSheetBehavior<FrameLayout> by lazy {
        BottomSheetBehavior.from(bottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private var bottomSheetValueAnimator: ValueAnimator? = null
    private var targetPeekHeight = 0

    private val viewModel: WebViewModel by lazy {
        ViewModelProviders.of(this).get(WebViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_payment)
//        overridePendingTransition(0, 0)

        viewModel.uri.value = intent.getTypedParams().uri

        bottomSheet.viewTreeObserver.addOnGlobalLayoutListener {

//            val fragment = supportFragmentManager.findFragmentByTag(WebFragment::class.java.name) as? WebFragment
//
//            if (fragment != null && fragment.isVisible()) {
//                transitionToPeekHeight(fragment.getDesiredPeekHeight())
//            } else {
                val newPeekHeight = bottomSheet.measuredHeight
                transitionToPeekHeight(newPeekHeight)
//            }
        }

//        getPaymentHandler().getPaymentSessionObservable()
//            .observe(this, object : com.adyen.checkout.core.Observer<PaymentSession>() {
//                fun onChanged(@NonNull paymentSession: PaymentSession) {
//                    mCheckoutViewModel.updateCheckoutMethodsViewModel(paymentSession)
//                }
//            })

//        ThemeUtil.applyPrimaryThemeColor(
//            this,
//            mProgressBar.getProgressDrawable(),
//            mProgressBar.getIndeterminateDrawable()
//        )

        if (savedInstanceState == null) {
            // Post this, otherwise ContentLoadingProgressBar will remove the callbacks to show itself.
            bottomSheet.post { progressBar.show() }
//            mCheckoutViewModel.getCheckoutMethodsLiveData()
//                .observeOnce(this, object : Observer<CheckoutMethodsModel>() {
//                    fun onChanged(@Nullable checkoutMethodsModel: CheckoutMethodsModel) {
//                        bottomSheet.post { progressBar.hide() }
                        showRequiredFragment()
//                    }
//                })
        }

        bottomSheetBehavior.setBottomSheetCallback(BottomSheetCallback())
    }

    private fun showRequiredFragment() {

//        val paymentReference = getPaymentReference()

        val fragmentManager = supportFragmentManager
        fragmentManager.executePendingTransactions()

        val webFragment = WebFragment()
        fragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_fragmentContainer, webFragment, WebFragment::class.java.name)
            .commit()

//        if (mCheckoutViewModel.getCheckoutMethodsLiveData().getPreselectedCheckoutMethod() != null) {
//            val fragment = PreselectedCheckoutMethodFragment.newInstance(paymentReference)
//            fragmentManager
//                .beginTransaction()
//                .replace(R.id.frameLayout_fragmentContainer, fragment, PreselectedCheckoutMethodFragment.TAG)
//                .addToBackStack(PreselectedCheckoutMethodFragment.TAG)
//                .commit()
//        }
    }

    private fun transitionToPeekHeight(newPeekHeight: Int) {

        if (newPeekHeight <= 0 || newPeekHeight == targetPeekHeight) {
            return
        }

        when(bottomSheetBehavior.getState()) {

            BottomSheetBehavior.STATE_HIDDEN -> {
                bottomSheetBehavior.peekHeight = newPeekHeight
                bottomSheet.post {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                targetPeekHeight = newPeekHeight
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                startBottomSheetAnimation(Math.max(0, bottomSheetBehavior.peekHeight), newPeekHeight)
                targetPeekHeight = newPeekHeight
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                bottomSheetBehavior.peekHeight = newPeekHeight
                targetPeekHeight = newPeekHeight
            }
            BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> targetPeekHeight = newPeekHeight
            else -> {}
        }
    }

    private fun startBottomSheetAnimation(initialPeekHeight: Int, newPeekHeight: Int) {
        cancelBottomSheetAnimation()
        bottomSheetValueAnimator = ValueAnimator.ofInt(initialPeekHeight, newPeekHeight)
        bottomSheetValueAnimator?.duration = PaymentActivity.BOTTOM_SHEET_ANIM_DURATION.toLong()
        bottomSheetValueAnimator?.interpolator = DecelerateInterpolator()
        bottomSheetValueAnimator?.addUpdateListener { animation ->
            bottomSheetBehavior.peekHeight = animation.animatedValue as Int
        }
        bottomSheetValueAnimator?.start()
    }

    private fun cancelBottomSheetAnimation() {
        if (bottomSheetValueAnimator != null) {
            bottomSheetValueAnimator?.cancel()
            bottomSheetValueAnimator = null
        }
    }

    private inner class BottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when(newState) {
//            BottomSheetBehavior.STATE_HIDDEN -> cancelCheckoutActivity()
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    val peekHeight = bottomSheetBehavior.getPeekHeight()

                    if (peekHeight != targetPeekHeight) {
                        startBottomSheetAnimation(peekHeight, targetPeekHeight)
                    }
                }
                BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior.peekHeight = targetPeekHeight
                else -> {}
            }
        }

        override fun onSlide(p0: View, p1: Float) {
            // n/a
        }
    }

}
