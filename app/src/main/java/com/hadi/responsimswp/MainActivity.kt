package com.hadi.responsimswp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import android.content.Intent
import android.util.Log


class MainActivity : AppCompatActivity() {

    lateinit var btnInput: FloatingActionButton
    lateinit var lvData: ListView
    var listData: ArrayList<m_data> = ArrayList()
    var list_prodi: ArrayList<String> = ArrayList()

    var nama1: String = ""
    var nim1: String = ""
    var prodi1: String = ""
    var id_prodi1: String = ""
    var id_mahasiswa1: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvData = findViewById(R.id.lvdata)

        input_data()

        fetch_data()

        action_lvClicked()


    }


    fun fetch_data() {

        AndroidNetworking.get(
            "https://latihan.aviljepara.com/fetch\n" +
                    "_mahasiswa.php"
        )
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val data: JSONArray? = response?.getJSONArray("data")

                    for (i in 0 until data?.length()!!) {
                        val item = data.getJSONObject(i)
                        listData.add(
                            m_data(
                                item.getInt("id_mahasiswa"),
                                item.getString("nim"),
                                item.getString("nama"),
                                item.getInt("id_prodi"),
                                item.getString("nama_prodi")
                            )
                        )
                    }


                    lvData = findViewById(R.id.lvdata)
                    val adp = adapter(this@MainActivity, listData)
                    lvData.adapter = adp


                }

                override fun onError(anError: ANError?) {
                    print("error")
                }

            })

    }

    fun input_data() {

        list_prodi.clear()

        btnInput = findViewById(R.id.btnInput)

        btnInput.setOnClickListener() {

            val mBuilder = LayoutInflater.from(this@MainActivity).inflate(R.layout.add_data, null)

            val alert = AlertDialog.Builder(this@MainActivity).setView(mBuilder)

            val alrt = alert.show()

            val Nama = mBuilder.findViewById<EditText>(R.id.Nama)
            val Nim = mBuilder.findViewById<EditText>(R.id.NIM)
            val spinner = mBuilder.findViewById<Spinner>(R.id.Spinner)
            val btnAdd = mBuilder.findViewById<Button>(R.id.btnTambah)
            val btnCanc = mBuilder.findViewById<Button>(R.id.btnBatal)
            val id_prodi = mBuilder.findViewById<TextView>(R.id.Result)


            AndroidNetworking.get("https://latihan.aviljepara.com/fetch_prodi.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        val data: JSONArray? = response?.getJSONArray("data")

                        for (i in 0 until data?.length()!!) {
                            val item = data.getJSONObject(i)
                            list_prodi.add(
                                item.getString("nama_prodi")
                            )
                        }
                        val arrayAdapter = ArrayAdapter(
                            this@MainActivity, android.R.layout.simple_spinner_item, list_prodi
                        )
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = arrayAdapter


                        spinner.onItemSelectedListener = object :

                            AdapterView.OnItemSelectedListener {
                            @SuppressLint("ResourceType")
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {


                                val valueID: Any = parent!!.selectedItemId+1
                                id_prodi.setText(valueID.toString())

                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }


                        }


                    }

                    override fun onError(anError: ANError?) {
                        TODO("Not yet implemented")
                    }

                })



            btnAdd.setOnClickListener() {

                AndroidNetworking.post("https://latihan.aviljepara.com/add_mahasiswa.php")
                    .addBodyParameter("nim", Nim.text.toString())
                    .addBodyParameter("nama", Nama.text.toString())
                    .addBodyParameter("id_prodi", id_prodi.text.toString())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            Toast.makeText(
                                this@MainActivity,
                                "Berhasil ditambahkan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onError(anError: ANError?) {
                            Toast.makeText(
                                this@MainActivity,
                                "Gagal Ditambahkam",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })

                list_prodi.clear()

                alrt.dismiss()


            }

            btnCanc.setOnClickListener() {

                list_prodi.clear()

                alrt.dismiss()

            }
        }
    }

    fun action_lvClicked() {

        lvData.setOnItemClickListener { parent, view, position, id ->

            AndroidNetworking.get(
                "https://latihan.aviljepara.com/fetch\n" +
                        "_mahasiswa.php"
            )

                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {
                        val data: JSONArray? = response?.getJSONArray("data")

                        val item = data?.getJSONObject(position)

                        id_mahasiswa1 = item!!.getInt("id_mahasiswa").toString()
                        nim1 = item.getString("nim")
                        nama1 = item.getString("nama")
                        id_prodi1 = item.getInt("id_prodi").toString()
                        prodi1 = item.getString("nama_prodi")



                        edit_data()


                    }

                    override fun onError(anError: ANError?) {
                        print("error")
                    }

                })

        }

    }

    fun edit_data() {

        list_prodi.clear()


        val view1 = LayoutInflater.from(this@MainActivity).inflate(R.layout.edit_data, null)

        val builder1 = AlertDialog.Builder(this@MainActivity).setView(view1)

        val alrt  = builder1.show()

        val namaEdit = view1.findViewById<EditText>(R.id.NamaEdit)
        val nimEdit = view1.findViewById<EditText>(R.id.NIMedit)
        val spinnerEdit = view1.findViewById<Spinner>(R.id.Spinner1)
        val ResultEdit = view1.findViewById<TextView>(R.id.Result1)
        val idMhswEdit = view1.findViewById<TextView>(R.id.id_mhsw)
        val btnEdit = view1.findViewById<Button>(R.id.btnEdit1)
        val btnBatal = view1.findViewById<Button>(R.id.btnBatal1)

        namaEdit.setText(nama1)
        nimEdit.setText(nim1)
        idMhswEdit.setText(id_mahasiswa1)


        AndroidNetworking.get("https://latihan.aviljepara.com/fetch_prodi.php")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    val data: JSONArray? = response?.getJSONArray("data")

                    for (i in 0 until data?.length()!!) {
                        val item = data.getJSONObject(i)
                        list_prodi.add(
                            item.getString("nama_prodi")
                        )
                    }
                    val arrayAdapter = ArrayAdapter(
                        this@MainActivity, android.R.layout.simple_spinner_item, list_prodi
                    )
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    spinnerEdit.adapter = arrayAdapter

                    spinnerEdit.setSelection(id_prodi1?.toInt()-1)

                    spinnerEdit.onItemSelectedListener = object :

                        AdapterView.OnItemSelectedListener {
                        @SuppressLint("ResourceType")
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {


                            val valueID: Any = parent!!.selectedItemId+1
                            ResultEdit.setText(valueID.toString())

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }


                    }


                }

                override fun onError(anError: ANError?) {
                    TODO("Not yet implemented")
                }

            })


        btnEdit.setOnClickListener() {

            AndroidNetworking.post("https://latihan.aviljepara.com/update_mahasiswa.php")
                .addBodyParameter("nim", nimEdit.text.toString())
                .addBodyParameter("nama", namaEdit.text.toString())
                .addBodyParameter("id_prodi", ResultEdit.text.toString())
                .addBodyParameter("id_mahasiswa", idMhswEdit.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        Toast.makeText(
                            this@MainActivity,
                            "Berhasil Di Edit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(
                            this@MainActivity,
                            "Gagal Di Edit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            list_prodi.clear()

            alrt.dismiss()


        }

        btnBatal.setOnClickListener() {
            list_prodi.clear()
            alrt.dismiss()
        }

    }

}
