package com.example.myshopper.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopper.R
import com.example.myshopper.models.Products



/**
 * A simple [CheckoutFragment] class.
 */

private const val TAG = "CheckoutFragment"

class CheckoutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var checkedItems: ArrayList<Products>? = arguments?.getParcelableArrayList("checkedItems")
        val list = ArrayList<Products>()
        list.add(Products(1, "Television", 250.00))
        if (checkedItems == null) checkedItems = list
        val button = view.findViewById<Button>(R.id.buttonCheckout)
        val adapter = MyAdapter(checkedItems!!, requireContext())
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rcv)
        Log.d(TAG, "onCreateView: Recyclerview called")
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        var totalPrice = 0.00
        checkedItems.forEach{
            totalPrice += it.price
        }
        Log.d(TAG, "onViewCreated: Price is $ $totalPrice")
        button!!.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Transaction Successful")
            builder.setMessage("Total Amount is \$ $totalPrice")
                .show()
        }
        val priceView = view.findViewById<TextView>(R.id.total_product_price)
        priceView.text =totalPrice.toString()
    }

    class MyAdapter(private val arrayList: ArrayList<Products>, val ctx: Context) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){
        private val checkedItems = mutableSetOf<Int>()
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bindItems(products: Products) {
                itemView.findViewById<TextView>(R.id.checked_product_name).text = products.name
                itemView.findViewById<TextView>(R.id.checked_product_price).text = products.price.toString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val list = LayoutInflater.from(parent.context).inflate(R.layout.checkout_list, parent, false)
            return ViewHolder(
                list
            )
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(arrayList[position])
        }
    }

    companion object {

    }
}