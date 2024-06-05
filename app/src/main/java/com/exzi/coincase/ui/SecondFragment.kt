package com.exzi.coincase.ui

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.exzi.coincase.R
import com.exzi.coincase.adapter.OrderBookAdapter
import com.exzi.coincase.data.bookList.BookListModel
import com.exzi.coincase.data.bookList.Buy
import com.exzi.coincase.data.candleData.CandleDataModel
import com.exzi.coincase.databinding.FragmentSecondBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.net.ssl.SSLSocketFactory

@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding get() = _binding!!

    private val dataList = mutableListOf<Buy>()
    private var adapter = OrderBookAdapter()
    private var pairId: Double? = null
    private var name: String? = null
    private val gson = Gson()
    private var socketJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        startSocketConnection()
        startSocketConnectionCandle()
        handleClickListener()

    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function to handle fragment arguments
     **/
    private fun handleArguments() {
        arguments?.let {
            name = arguments?.getString("name")
            pairId = arguments?.getDouble("pairId")

        }
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function to handle click events
     **/
    private fun handleClickListener() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function to start socket connection for order book data
     **/
    private fun startSocketConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                try {
                    val sslSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
                    val socket = sslSocketFactory.createSocket("socket.exzi.com", 443) as Socket

                    val output = PrintWriter(socket.getOutputStream(), true)
                    val input = BufferedReader(InputStreamReader(socket.getInputStream()))

                    output.println("GET /book/list?pair_id=1041&buy=1&sell=1 HTTP/1.1")
                    output.println("Host: socket.exzi.com")
                    output.println("Connection: close")
                    output.println()
                    output.flush()

                    var line: String?
                    val responseBody = StringBuilder()
                    var isBody = false

                    while (input.readLine().also { line = it } != null) {
                        if (isBody) {
                            responseBody.append(line)
                        }
                        if (line.isNullOrEmpty()) {
                            isBody = true
                        }
                    }

                    val gson = Gson()
                    val bookListModel =
                        gson.fromJson(responseBody.toString(), BookListModel::class.java)
                    withContext(Dispatchers.Main) {
                        dataList.clear()
                        dataList.addAll(bookListModel.buy)
                        updateRecyclerView()
                    }

                    output.close()
                    input.close()
                    socket.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                delay(3000) //wait for 3 seconds then update
            }
        }
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function to start socket connection for candle data
     **/
    private fun startSocketConnectionCandle() {
        socketJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                try {
                    val sslSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
                    val socket = sslSocketFactory.createSocket("socket.exzi.com", 443) as Socket

                    val output = PrintWriter(socket.getOutputStream(), true)
                    val input = BufferedReader(InputStreamReader(socket.getInputStream()))

                    output.println("GET /graph/hist?t=BTCUSDT&r=D&limit=5000&end=1705363200 HTTP/1.1")
                    output.println("Host: socket.exzi.com")
                    output.println("Connection: close")
                    output.println()
                    output.flush()

                    val responseBody = StringBuilder()
                    var isBody = false

                    input.forEachLine { line ->
                        if (isBody) {
                            responseBody.append(line)
                        }
                        if (line.isEmpty()) {
                            isBody = true
                        }
                    }

                    val jsonResponse = responseBody.toString()
                    val dataType = object : TypeToken<List<CandleDataModel>>() {}.type
                    val data: List<CandleDataModel> = gson.fromJson(jsonResponse, dataType)

                    withContext(Dispatchers.Main) {
                        updateCandleChart(arrayListOf(data[0]))
                    }

                    output.close()
                    input.close()
                    socket.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                delay(3000) //wait for 3 seconds then update
            }
        }
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function to update the candle chart with new data
     **/
    private fun updateCandleChart(data: List<CandleDataModel>) {
        val entries = data.mapNotNull {
            val time = it.time ?: return@mapNotNull null
            val high = it.high ?: return@mapNotNull null
            val low = it.low ?: return@mapNotNull null
            val open = it.open ?: return@mapNotNull null
            val close = it.close ?: return@mapNotNull null
            CandleEntry(
                time.toFloat(),
                high.toFloat(),
                low.toFloat(),
                open.toFloat(),
                close.toFloat()
            )
        }

        val dataSet = CandleDataSet(entries, "BTCUSDT")
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.black)
        dataSet.shadowColor = ContextCompat.getColor(requireContext(), R.color.black)
        dataSet.shadowWidth = 0.8f
        dataSet.decreasingColor = ContextCompat.getColor(requireContext(), R.color.red)
        dataSet.decreasingPaintStyle = Paint.Style.FILL
        dataSet.increasingColor = ContextCompat.getColor(requireContext(), R.color.green)
        dataSet.increasingPaintStyle = Paint.Style.FILL
        dataSet.neutralColor = ContextCompat.getColor(requireContext(), R.color.black)

        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        dataSet.valueTextSize = 10f

        val candleData = CandleData(dataSet)

        binding.candleChart.data = candleData

        val yAxisLeft: YAxis = binding.candleChart.axisLeft
        yAxisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        val yAxisRight: YAxis = binding.candleChart.axisRight
        yAxisRight.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        val xAxis: XAxis = binding.candleChart.xAxis
        xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        val description = binding.candleChart.description
        description.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        val legend = binding.candleChart.legend
        legend.textColor = ContextCompat.getColor(requireContext(), R.color.white)

        binding.candleChart.setNoDataTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )

        binding.candleChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        socketJob?.cancel()
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function to update the RecyclerView with new data
     **/
    private fun updateRecyclerView() {
        CoroutineScope(Dispatchers.Main).launch {
            adapter.setList(dataList)
        }
    }


}