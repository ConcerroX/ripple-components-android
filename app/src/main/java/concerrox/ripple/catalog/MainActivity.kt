package concerrox.ripple.catalog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import concerrox.ripple.catalog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private object Theme {
        var isMaterial3Enabled: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(
            if (Theme.isMaterial3Enabled) {
                com.google.android.material.R.style.Theme_Material3_DayNight
            } else {
                com.google.android.material.R.style.Theme_MaterialComponents_DayNight
            }
        )
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.spinner.entries =
            listOf("Apple", "Banana", "Pear", "Grape")
        binding.themeButton.setOnClickListener {
            Theme.isMaterial3Enabled = !Theme.isMaterial3Enabled
            recreate()
        }
        binding.switchBar.text = "Test"
    }
}