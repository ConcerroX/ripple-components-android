package concerrox.ripple.datavisualization

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import concerrox.ripple.Material
import concerrox.ripple.internal.UiUtils.dp2px
import concerrox.ripple.internal.toDegrees
import concerrox.ripple.internal.toRadians
import kotlin.math.cos
import kotlin.math.sin
@Deprecated("")
class DonutChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        0
    } else {
        0
    }
) : View(context, attrs, defStyleAttr) {

    data class ChartItem(var label: CharSequence, var value: Float) {
        internal var path: Path? = null
    }

    private val itemList = mutableListOf<ChartItem>()
    private val itemPaint = Paint().apply {
        isAntiAlias = true
    }

    //    private val itemDrawingPath = Path()
    private var totalValue: Float = 0f
    private var chartRect = RectF()
    var chartRadius = dp2px(72f, context).toFloat()
    var chartItemGap = dp2px(4f, context).toFloat()
    var chartTrackThickness = dp2px(16f, context).toFloat()

    init {
        addItems(
            listOf(
                newItem("Apple", 1730f), newItem("Google", 2130f), newItem("Microsoft", 1200f)
            )
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (itemList.isEmpty()) {

        } else {
            val radius = dp2px(72f, context)
            val halfItemGap = chartItemGap / 2f
            val centerX = width / 2f
            val centerY = height / 2f
            itemList.forEachIndexed { index, it ->
                itemPaint.setColor((if (index == 0) 0xff6200ee else (if (index == 1) 0xff5f6368 else 0xff1a73e8)).toInt())
//                itemPaint.style = Paint.Style.STROKE
//                it.path?.let { it1 -> canvas.drawPath(it1, itemPaint) }
                canvas.drawPath(it.path!!, itemPaint)
            }

//            val p = Path().apply { addCircle(centerX, centerY, radius.toFloat(), Path.Direction.CW) }
//            itemPaint.setColor(0x30000000)
//            canvas.drawPath(p, itemPaint)
        }
        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val radius = dp2px(72f, context)
        val centerX = width / 2f
        val centerY = height / 2f
        chartRect.set(
            centerX - radius, centerY - radius, centerX + radius, centerY + radius
        )
        updateItemPaths()
    }

    fun newItem(label: CharSequence, value: Float): ChartItem {
        return ChartItem(label, value)
    }

    fun addItem(item: ChartItem, updateChart: Boolean = true) {
        itemList.add(item)
        totalValue += item.value
        if (updateChart) updateChart()
    }

    fun addItems(items: List<ChartItem>) {
        items.forEach {
            addItem(it, false)
        }
        updateChart()
    }

    private fun updateItemPaths() {
        val centerX = width / 2f
        val centerY = height / 2f
        var startAngle = toRadians(-90f)
        val clippedInnerCircle = Path().apply {
            addCircle(centerX, centerY, chartRadius - chartTrackThickness, Path.Direction.CW)
        }
        itemList.forEach {
            val angle = toRadians(it.value / totalValue * 360f)
            val endAngle = startAngle + angle
            val midAngle = (endAngle + startAngle) / 2f
            it.path = Path().apply {
                moveTo(centerX, centerY)
                lineTo(centerX + chartRadius * cos(startAngle), centerY + chartRadius * sin(startAngle))
                arcTo(chartRect, toDegrees(startAngle), toDegrees(angle))
                moveTo(centerX + chartRadius * cos(endAngle), centerY + chartRadius * sin(endAngle))
                lineTo(centerX, centerY)
                op(Path().apply {
                    val offsetX = chartItemGap / 2 * cos(midAngle)
                    val offsetY = chartItemGap / 2 * sin(midAngle)
                    moveTo(centerX + offsetX, centerY + offsetY)
                    rLineTo(chartRadius * cos(startAngle), chartRadius * sin(startAngle))
                    rLineTo(-offsetX * 2, -offsetY * 2)
                    rLineTo(-chartRadius * cos(startAngle), -chartRadius * sin(startAngle))
                    rLineTo(chartRadius * cos(endAngle), chartRadius * sin(endAngle))
                    rLineTo(offsetX * 2, offsetY * 2)
                    rLineTo(chartRadius * cos(endAngle), chartRadius * sin(endAngle))
                }, Path.Op.DIFFERENCE)
                op(clippedInnerCircle, Path.Op.DIFFERENCE)
            }
            startAngle = endAngle
        }
    }

    private fun updateChart() {

    }

}