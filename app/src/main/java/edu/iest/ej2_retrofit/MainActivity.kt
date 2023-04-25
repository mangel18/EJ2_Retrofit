    package edu.iest.ej2_retrofit

    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import android.os.Bundle
    import android.widget.Button
    import androidx.appcompat.app.AppCompatActivity
    import com.squareup.picasso.Picasso
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import android.widget.ImageView
    import android.widget.Switch
    import android.widget.TextView
    import edu.iest.ej2_retrofit.api.ApiClient
    import edu.iest.ej2_retrofit.api.DogApi


    class MainActivity : AppCompatActivity() {

        private lateinit var ivPerro: ImageView
        private lateinit var tvUrl: TextView
        private lateinit var saveImageSwitch: Switch
        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private var isNewImage: Boolean = false

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Inicializa los objetos sharedPreferences y editor
            sharedPreferences = getPreferences(Context.MODE_PRIVATE)
            editor = sharedPreferences.edit()

            ivPerro = findViewById(R.id.ivPerro)
            tvUrl = findViewById(R.id.tvUrl)
            saveImageSwitch = findViewById(R.id.saveImageSwitch)

            // Restaura el estado guardado del switch
            saveImageSwitch.isChecked = sharedPreferences.getBoolean("saveImageSwitchState", false)

            // Configura el listener del switch
            saveImageSwitch.setOnCheckedChangeListener { _, isChecked ->
                // Guarda el estado del switch en las preferencias
                editor.putBoolean("saveImageSwitchState", isChecked)
                editor.apply()
            }

            if (saveImageSwitch.isChecked) {
                // Carga la imagen guardada si el switch está activado
                val savedImageUrl = sharedPreferences.getString("savedImageUrl", "")
                if (!savedImageUrl.isNullOrEmpty()) {
                    Picasso.get().load(savedImageUrl).into(ivPerro)
                    tvUrl.text = savedImageUrl
                } else {
                    // Si no hay imagen guardada, carga una imagen aleatoria
                    loadRandomDogImage()
                }
            } else {
                // Si el switch está desactivado, carga una imagen aleatoria
                loadRandomDogImage()
            }

            val bnRaza = findViewById<Button>(R.id.bnRaza)
            bnRaza.setOnClickListener {
                val intent = Intent(this, Razas::class.java)
                startActivity(intent)
            }


        }

        private fun loadRandomDogImage() {
            val service = ApiClient.getClient().create(DogApi::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = service.getRandomDog()
                    val imageUrl = response.imageUrl
                    runOnUiThread {
                        Picasso.get().load(imageUrl).into(ivPerro)
                        tvUrl.text = imageUrl
                        if (!isNewImage && saveImageSwitch.isChecked) {
                            // Guarda la URL de la imagen en las preferencias solo si es una imagen nueva
                            editor.putString("savedImageUrl", imageUrl)
                            editor.apply()
                        }
                        isNewImage = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
