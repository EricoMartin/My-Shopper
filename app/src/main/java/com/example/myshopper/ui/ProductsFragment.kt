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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopper.MainActivity
import com.example.myshopper.R
import com.example.myshopper.models.Products

/**
 * A simple [ProductsFragment] class
 */

private const val TAG = "ProductsFragment"
class ProductsFragment : Fragment() {

    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<Products>()
        list.add(Products(1, "Television", 250.00))
        list.add(Products(2, "Phone", 150.00))
        list.add(Products(3, "Refrigerator", 500.00))
        list.add(Products(4, "Bluetooth Speaker", 125.00))
        list.add(Products(5, "Air Conditioner", 700.55))
        list.add(Products(6, "Laptop", 450.00))
        list.add(Products(7, "Microwave", 975.50))
        list.add(Products(8, "Piano", 200.00))
        list.add(Products(9, "Microphone", 399.00))
        list.add(Products(10, "Decoder", 319.50))

        button = view.findViewById(R.id.btn_cart)
        val adapter = Adapter(list, requireContext())
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler)
        Log.d(TAG, "onCreateView: Recyclerview called")
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        button!!.setOnClickListener {
            val fragment = CheckoutFragment()
            val checkedItems = adapter.getCheckedItems()
            val bundle = Bundle()
            bundle.putParcelableArrayList("checkedItems", ArrayList(checkedItems))
            fragment.arguments = bundle

            (activity as? MainActivity)?.navigateToNewFragment(bundle)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.frame, fragment)
//                .addToBackStack(null)
//                .commit()
        }
    }

    class Adapter(private val arrayList: ArrayList<Products>, val ctx: Context) :
        RecyclerView.Adapter<Adapter.ViewHolder>(){
        private var checkoutList: MutableList<Products> = mutableListOf<Products>()
        private val checkedItems = mutableSetOf<Int>()
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val checkBox = itemView.findViewById<CheckBox>(R.id.checked)
            fun bindItems(products: Products) {
                itemView.findViewById<TextView>(R.id.product_name).text = products.name
                itemView.findViewById<TextView>(R.id.product_price).text = products.price.toString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val list = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return ViewHolder(
                list
            )
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(arrayList[position])

            holder.checkBox.setOnCheckedChangeListener(null) // Clear previous listener

            holder.checkBox.isChecked = checkedItems.contains(position)

            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedItems.add(position)
                } else {
                    checkedItems.remove(position)
                }
            }
        }
        fun getCheckedItems(): List<Products> {
            return checkedItems.map { arrayList[it] }
        }
    }

    companion object {

    }
}