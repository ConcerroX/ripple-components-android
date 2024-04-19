package concerrox.ripple.catalog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import concerrox.ripple.catalog.databinding.ActivityMainBinding
import concerrox.ripple.spinner.MaterialSpinner
import concerrox.ripple.switches.MaterialSwitchBar

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
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<MaterialSpinner>(R.id.spinner).entries =
            listOf("Apple", "Banana", "Pear", "Grape")
        findViewById<MaterialButton>(R.id.theme_button).setOnClickListener {
            Theme.isMaterial3Enabled = !Theme.isMaterial3Enabled
            recreate()
        }
//        findViewById<MaterialSwitchBar>(R.id.switch_bar).text = "Test"
    }
}