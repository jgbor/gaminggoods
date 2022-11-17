package hu.bme.aut.android.gaminggoods.feature.details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.databinding.FragmentDetailsBinding
import hu.bme.aut.android.gaminggoods.model.DealData
import kotlin.concurrent.thread

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private var dealData: DealData? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDetailsBinding.inflate(layoutInflater)

        dealData = args.dealData

        activity?.title = getString(R.string.deal,dealData?.id)

        binding.nameTextView.text = dealData?.title
        binding.platformTextView.text = getString(R.string.platfromtext,dealData?.platforms)
        binding.endDateTextView.text = getString(R.string.datetext,if (dealData?.end_date!="N/A") dealData?.end_date else getString(R.string.unknown))
        binding.descTextView.movementMethod = ScrollingMovementMethod()
        binding.descTextView.text = dealData?.description
        thread {
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(dealData?.image).openStream()
                image = BitmapFactory.decodeStream(`in`)
                activity?.runOnUiThread {
                    binding.imageView.setImageBitmap(image)
                }
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
        }

        binding.browserButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dealData?.open_giveaway_url))
            startActivity(browserIntent)
        }

        return binding.root
    }
}