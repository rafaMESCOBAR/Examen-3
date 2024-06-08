package com.mallasca.rafael.laboratoriocalificado03

// Ejercicio01Activity.kt

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.mallasca.rafael.laboratoriocalificado03.databinding.ActivityEjercicio01Binding
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Ejercicio01Activity : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicio01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Llamada a la API utilizando Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://private-effe28-tecsup1.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.getTeachers().enqueue(object : Callback<List<Teacher>> {
            override fun onResponse(call: retrofit2.Call<List<Teacher>>, response: Response<List<Teacher>>) {
                if (response.isSuccessful) {
                    val teachers = response.body()
                    if (teachers != null) {
                        val adapter = TeacherAdapter(teachers, onItemClick = { teacher ->
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${teacher.telefono}"))
                            startActivity(intent)
                        }, onItemLongClick = { teacher ->
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:")
                                putExtra(Intent.EXTRA_EMAIL, arrayOf(teacher.correo))
                            }
                            startActivity(intent)
                        })
                        binding.recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@Ejercicio01Activity, getString(R.string.error_data), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Teacher>>, t: Throwable) {
                Toast.makeText(this@Ejercicio01Activity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
            }
        })
    }

    interface ApiService {
        @GET("list/teacher")
        fun getTeachers(): retrofit2.Call<List<Teacher>>
    }

    data class Teacher(
        val nombre: String,
        val apellido: String,
        val foto: String,
        val telefono: String,
        val correo: String
    )
}