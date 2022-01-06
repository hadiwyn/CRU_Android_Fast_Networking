package com.hadi.responsimswp


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class adapter(val context: Context, val data: ArrayList<m_data>): BaseAdapter() {

    lateinit var tvnama: TextView
    lateinit var tvnim: TextView
    lateinit var tvprodi: TextView
    lateinit var id_mahasiswa: TextView
    lateinit var id_prodi: TextView

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewData = convertView
        viewData = LayoutInflater.from(context).inflate(R.layout.list_data, parent, false)


        tvnim = viewData.findViewById(R.id.NIM)
        tvnama = viewData.findViewById(R.id.nama)
        tvprodi = viewData.findViewById(R.id.prodi)
        id_mahasiswa = viewData.findViewById(R.id.id_mahasiswa)
        id_prodi = viewData.findViewById(R.id.id_prodi)

        tvnama.text = data[position].nama
        tvnim.text = data[position].nim
        id_prodi.text = data[position].id_prodi.toString()
        id_mahasiswa.text = data[position].id_mahasiswa.toString()
        tvprodi.text = data[position].nama_prodi


        return viewData

    }



}