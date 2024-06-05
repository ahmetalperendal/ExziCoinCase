package com.exzi.coincase.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exzi.coincase.data.bookList.Buy
import com.exzi.coincase.databinding.ItemOrderBinding

class OrderBookAdapter : RecyclerView.Adapter<OrderBookAdapter.OrderBookAdapterViewHolder>() {

    private var _list = ArrayList<Buy>()
    val list: List<Buy> get() = _list
    private lateinit var context: Context

    @SuppressLint("NotifyDataSetChanged")
    fun setList(rList: List<Buy>) {
        _list.clear()
        _list.addAll(rList)
        notifyDataSetChanged()
    }

    class OrderBookAdapterViewHolder(var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBookAdapterViewHolder {
        context = parent.context
        return OrderBookAdapterViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderBookAdapterViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            val volumeString = currentItem.rateF
            if (volumeString != null) {
                try {
                    val volume = volumeString.toDouble()
                    updateViewWidth(buyWeightBgView, (volume / 1000).toInt())
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    // Hata durumunda yapılacak işlemleri buraya ekleyebilirsiniz
                }
            }

            val volumeString2 = currentItem.rate
            if (volumeString2 != null) {
                try {
                    val volume = volumeString2.toDouble()
                    updateViewWidth(sellWeightBgView, (volume / 1000).toInt())
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    // Hata durumunda yapılacak işlemleri buraya ekleyebilirsiniz
                }
            }

            quantityBuyTxt.text = currentItem.volumeF
            buyPriceTxt.text = currentItem.rateF
            sellPriceTxt.text = currentItem.rate.toString().substring(0, 7)
            sellQuantityTxt.text = currentItem.volume.toString()

            // Logları ekleyerek currentItem değerlerini kontrol edelim
            Log.d("OrderBookAdapter", "Position: $position, RateF: ${currentItem.rateF}, Rate: ${currentItem.rate}, VolumeF: ${currentItem.volumeF}, Volume: ${currentItem.volume}")
        }
    }


    fun updateViewWidth(view: View, width: Int) {
        Log.d("updateViewWidth", "Updating width to: $width")
        val layoutParams = view.layoutParams
        layoutParams.width = width
        view.layoutParams = layoutParams
    }


    override fun getItemCount(): Int = list.size
}
