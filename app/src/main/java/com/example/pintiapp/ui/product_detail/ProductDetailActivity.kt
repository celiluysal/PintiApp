package com.example.pintiapp.ui.product_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ActivityProductDetailBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.models.Record
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.getProgressDrawable
import com.example.pintiapp.base.BaseActivity
import com.example.pintiapp.utils.loadImage
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlin.collections.ArrayList

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding, ViewModel>(),
    RecordRecyclerViewAdapter.OnMarketItemClickListener,
    ViewModelStoreOwner{

    private lateinit var recordRecyclerViewAdapter : RecordRecyclerViewAdapter
//    private var mInterstitialAd: InterstitialAd? = null
//    private val TAG = "ProductDetailActivity"


    override fun getViewBinding(): ActivityProductDetailBinding {
        Log.e("a","getViewBinding")

        return ActivityProductDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("a","onCreate")

//        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
//        mInterstitialAd.loadAd(AdRequest.Builder().build


//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.e(TAG, adError.message);
//                mInterstitialAd = null
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                Log.e(TAG, "Ad was loaded.");
//                mInterstitialAd = interstitialAd
//            }
//        })
//
//        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
//            override fun onAdDismissedFullScreenContent() {
//                Log.d(TAG, "Ad was dismissed.");
//            }
//
//            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                Log.d(TAG, "Ad failed to show.");
//            }
//
//            override fun onAdShowedFullScreenContent() {
//                Log.d(TAG, "Ad showed fullscreen content.");
//                mInterstitialAd = null;
//            }
//        }
//
//        mInterstitialAd?.show(this) ?: Log.e("TAG", "The interstitial ad wasn't ready yet.")

        setToolbar()

        binding.adView.loadAd(adRequest)

        val product = intent.extras?.get("product") as Product
        setFields(product)
    }




    fun setFields(product: Product) {
        binding.imageViewProduct.loadImage(product.photoURL, getProgressDrawable(this))
        binding.textViewProductName.text = product.name
        val brand = product.brand
        val category = CategoryStatic.shared.getCategoryName(product.categoryId)
        val text = "$brand - $category"
        binding.textViewProductBrandAndCategory.text = text

        binding.recyclerviewRecords.layoutManager = LinearLayoutManager(this)
        recordRecyclerViewAdapter = RecordRecyclerViewAdapter(ArrayList(product.records), this)
        binding.recyclerviewRecords.adapter = recordRecyclerViewAdapter
    }

    private fun setToolbar() {
        setSupportActionBar(binding.includeToolbar.mainToolbar)
        binding.includeToolbar.imageViewSearch.visibility = ImageView.INVISIBLE
        binding.includeToolbar.imageViewBack.visibility = ImageView.VISIBLE

        setSupportActionBar(binding.includeToolbar.mainToolbar)

        binding.includeToolbar.imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onMarketCardClick(item: Record, position: Int) {
        Log.e("adf","ProductDetailActivity onMarketCardClick")

        // Map point based on address
        val mapIntent: Intent = Uri.parse(
            "https://www.google.com/maps/search/?api=1&query="+item.locationCoordinate
        ).let { location ->
            // Or map point based on latitude/longitude
            // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
            Intent(Intent.ACTION_VIEW, location)
        }

        startActivity(mapIntent)
    }


}