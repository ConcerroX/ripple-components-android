package concerrox.ripple.catalog

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.color.MaterialColors
import concerrox.ripple.avatar.TextAvatarDrawable
import concerrox.ripple.catalog.databinding.ActivityMainBinding
import concerrox.ripple.catalog.databinding.PageListBinding
import concerrox.ripple.catalog.databinding.PageMainBinding
import concerrox.ripple.catalog.databinding.PageMenuBinding
import concerrox.ripple.drawable.LayerDrawableCompat
import concerrox.ripple.featurediscovery.FeatureDiscoveryPromptHelper
import concerrox.ripple.internal.dp
import concerrox.ripple.tooltip.MaterialTooltip

class MainActivity : AppCompatActivity() {

    private companion object {
        var isMaterial3Enabled: Boolean = false
    }

    private var pageList: List<Pair<String, ViewBinding>> = listOf()

    @androidx.annotation.OptIn(com.google.android.material.badge.ExperimentalBadgeUtils::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(
            if (isMaterial3Enabled) {
                com.google.android.material.R.style.Theme_Material3_DayNight_NoActionBar
            } else {
                concerrox.ripple.R.style.Theme_Ripple_Material2
            }
        )
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appbar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val mainBinding = PageMainBinding.inflate(layoutInflater)
        val menuBinding = PageMenuBinding.inflate(layoutInflater)
        val listBinding = PageListBinding.inflate(layoutInflater)
        pageList = listOf(
            "Lists" to listBinding, "Menus" to menuBinding, "Main" to mainBinding,
        )
        binding.viewPager.adapter = MainPagerAdapter(this, pageList)
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.switch_m2_m3 -> {
                    isMaterial3Enabled = !isMaterial3Enabled
                    recreate()
                }
            }
            true
        }
        menuBinding.test.setOnClickListener {
            menuBinding.test.post {
                FeatureDiscoveryPromptHelper.showFromView(menuBinding.test)
            }
        }

        mainBinding.switchBar.text = "Test"
        MaterialTooltip(mainBinding.switchBar, "TETSTTSTS").show()

        mainBinding.avatar1.setImageResource(R.drawable.ic_placeholder_circle_24)
        mainBinding.avatar1.statusIndicatorDrawable = AppCompatResources.getDrawable(
            this, concerrox.ripple.R.drawable.avatar_status_indicator_active
        )
        mainBinding.avatar2.setImageResource(R.drawable.ic_placeholder_circle_24)
        mainBinding.avatar2.statusIndicatorDrawable = AppCompatResources.getDrawable(
            this, concerrox.ripple.R.drawable.avatar_status_indicator_do_not_disturb
        )
        mainBinding.avatar3.setImageResource(R.drawable.ic_placeholder_circle_24)
        mainBinding.avatar3.statusIndicatorDrawable = AppCompatResources.getDrawable(
            this, concerrox.ripple.R.drawable.avatar_status_indicator_set_as_away
        )
        mainBinding.avatar9.setImageDrawable(AppCompatResources.getDrawable(
            this, R.drawable.ic_launcher_foreground
        )?.apply {
            setTint(0xff000000.toInt())
        })
        mainBinding.avatar9.statusIndicatorDrawable = AppCompatResources.getDrawable(
            this, concerrox.ripple.R.drawable.avatar_status_indicator_set_as_away
        )
        mainBinding.avatar9.isBorderEnabled = true

        mainBinding.avatar4.setImageDrawable(TextAvatarDrawable("陈"))
        mainBinding.avatar4.statusIndicatorDrawable = AppCompatResources.getDrawable(
            this, concerrox.ripple.R.drawable.avatar_status_indicator_active
        )
        mainBinding.avatar5.setImageDrawable(TextAvatarDrawable("G").apply {
            color = MaterialColors.getColor(
                this@MainActivity, com.google.android.material.R.attr.colorSecondary, 0
            )
        })
        mainBinding.avatar5.statusIndicatorDrawable = AppCompatResources.getDrawable(
            this, concerrox.ripple.R.drawable.avatar_status_indicator_do_not_disturb
        )
        mainBinding.avatar6.setImageDrawable(
            LayerDrawableCompat(
                arrayOf(ColorDrawable(0xff6200ee.toInt()), AppCompatResources.getDrawable(
                    this, concerrox.ripple.R.drawable.avatar_placeholder
                )?.apply {
                    setTint(0xffffffff.toInt())
                })
            )
        )
//        mainBinding.avatar6.statusIndicatorDrawable = AppCompatResources.getDrawable(
//            this, concerrox.ripple.R.drawable.avatar_status_indicator_set_as_away
//        )
        mainBinding.avatar7.setImageDrawable(
            LayerDrawableCompat(
                arrayOf(ColorDrawable(0xffc8fff4.toInt()), AppCompatResources.getDrawable(
                    this, concerrox.ripple.R.drawable.avatar_placeholder
                )?.apply {
                    setTint(
                        MaterialColors.getColor(
                            this@MainActivity,
                            com.google.android.material.R.attr.colorSecondary,
                            0
                        )
                    )
                })
            )
        )
        mainBinding.avatar8.setImageDrawable(LayerDrawableCompat(
            arrayOf(ColorDrawable(0xfff2e7fe.toInt()), AppCompatResources.getDrawable(
                this, concerrox.ripple.R.drawable.outline_account_circle_24
            )?.apply {
                setTint(
                    MaterialColors.getColor(
                        this@MainActivity,
                        com.google.android.material.R.attr.colorPrimary,
                        0
                    )
                )
            })
        ).apply {
            setLayerInset(1, 4.dp, 4.dp, 4.dp, 4.dp)
        })
//        mainBinding.avatar7.isBorderEnabled = true

        menuBinding.spinner.entries = listOf("Apple", "Banana", "Pear", "Grape")

        val bd = BadgeDrawable.create(this).apply {
//            setBounds(0, 0, 30, 30)
//            isVisible = true
            number = 8
//            badgeGravity = BadgeDrawable.TOP_END
//            updateBadgeCoordinates(mainBinding.avatar)
            val r = Rect()
            mainBinding.avatar1.getDrawingRect(r)
            bounds = r
            verticalOffset = 22
            horizontalOffset = 30
        }
//        BadgeUtils.attachBadgeDrawable(bd,mainBinding.avatar)
//        mainBinding.avatar.foregroundCompat = bd
        val f = BadgeUtils::class.java.getDeclaredField("USE_COMPAT_PARENT")
        f.isAccessible = true
        f.setBoolean(null, true)
        BadgeUtils.attachBadgeDrawable(bd, mainBinding.avatar1)









//        mainBinding.avatar.overlay.add(bd)
//        mainBinding.avatar.clipToOutline = false
//        (mainBinding.avatar.parent as ViewGroup).clipChildren = false
//        bd.atta
//        bd.updateBadgeCoordinates(mainBinding.avatar, mainBinding.avatar.parent as ViewGroup)
    }

}