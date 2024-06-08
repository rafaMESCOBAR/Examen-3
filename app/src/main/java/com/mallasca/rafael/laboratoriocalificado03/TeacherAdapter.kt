package com.mallasca.rafael.laboratoriocalificado03

// TeacherAdapter.kt

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.mallasca.rafael.laboratoriocalificado03.databinding.ItemTeacherBinding

class TeacherAdapter(
    private val teachers: List<Ejercicio01Activity.Teacher>,
    private val onItemClick: (Ejercicio01Activity.Teacher) -> Unit,
    private val onItemLongClick: (Ejercicio01Activity.Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val teacher = teachers[position]
                    onItemClick(teacher)
                }
            }

            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val teacher = teachers[position]
                    onItemLongClick(teacher)
                }
                true
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(teacher: Ejercicio01Activity.Teacher) {
            binding.textViewName.text = "${teacher.nombre} ${teacher.apellido}"
            Glide.with(binding.imageViewPhoto.context)
                .load(teacher.foto)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error) // <-- Esta línea
                .into(binding.imageViewPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teacher = teachers[position]
        holder.bind(teacher)
    }

    override fun getItemCount(): Int {
        return teachers.size
    }
}