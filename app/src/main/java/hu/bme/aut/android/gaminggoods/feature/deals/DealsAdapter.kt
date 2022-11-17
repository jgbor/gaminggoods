package hu.bme.aut.android.gaminggoods.feature.deals

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.databinding.ItemDealBinding
import hu.bme.aut.android.gaminggoods.model.DealData
import kotlin.concurrent.thread


class DealsAdapter(private val listener: OnDealSelectedListener, private val context: Fragment): RecyclerView.Adapter<DealsAdapter.DealViewHolder>() {
    private val deals: MutableList<DealData?> = ArrayList()

    interface OnDealSelectedListener {
        fun onDealSelected(deal: DealData?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deal, parent, false)
        return DealViewHolder(view)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val item = deals[position]
        holder.bind(item)
    }

    fun addDeal(deal: DealData?){
        deals.add(deal)
        notifyItemInserted(deals.size - 1)
    }

    override fun getItemCount(): Int= deals.size

    inner class DealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var binding = ItemDealBinding.bind(itemView)
        var item: DealData? = null

        init {
            binding.root.setOnClickListener { listener.onDealSelected(item) }
        }

        fun bind(newDeal: DealData?) {
            item = newDeal
            binding.DealItemNameText.text = item?.title
            binding.DealItemPlatformText.text = item?.platforms
            //DownloadImageFromInternet(binding.imageView).execute(item?.thumbnail)
            thread {
                var image: Bitmap? = null
                try {
                    val `in` = java.net.URL(item?.thumbnail).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    context.activity?.runOnUiThread {
                        binding.imageView.setImageBitmap(image)
                    }
                }
                catch (e: Exception) {
                    Log.e("Error Message", e.message.toString())
                    e.printStackTrace()
                }
            }
        }
    }
}