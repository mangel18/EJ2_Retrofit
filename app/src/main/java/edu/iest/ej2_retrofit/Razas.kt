package edu.iest.ej2_retrofit

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class Razas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razas)

        // Obtener el array de opciones de las razas
        val razasArray = resources.getStringArray(R.array.razas_array)

        // Crear un ArrayAdapter con las opciones del array
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, razasArray)

        // Establecer el adaptador en el Spinner
        val razas_spinner: Spinner = findViewById(R.id.razas_spinner)
        razas_spinner.adapter = adapter

        val spinner: Spinner = findViewById(R.id.razas_spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val razaSeleccionada = parent.getItemAtPosition(position).toString()
                mostrarImagenes(razaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se ha seleccionado ninguna opción
            }
        }
    }

    private fun mostrarImagenes(raza: String) {
        val imagenes: List<Int> = when (raza) {
            "Chihuahua" -> listOf(R.drawable.chihuahua1, R.drawable.chihuahua2, R.drawable.chihuahua3)
            "Salchicha" -> listOf(R.drawable.salchicha1, R.drawable.salchicha2, R.drawable.salchicha3)
            "Husky" -> listOf(R.drawable.husky1, R.drawable.husky2, R.drawable.husky3)
            else -> listOf() // Raza no reconocida
        }

        // Mostrar las imágenes en una vista de imagen
        val imagen1: ImageView = findViewById(R.id.imagen1)
        imagen1.setImageResource(imagenes[0])

        val imagen2: ImageView = findViewById(R.id.imagen2)
        imagen2.setImageResource(imagenes[1])

        val imagen3: ImageView = findViewById(R.id.imagen3)
        imagen3.setImageResource(imagenes[2])
    }
}
