package com.showcase.aboutdevice

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Skin(val label: String) {
    COLOROS17("ColorOS 17"), COLOROS15("ColorOS 15"), OXYGEN15("OxygenOS 15"),
    REALME7("realme UI 7"), HYPEROS4("HyperOS 4"), ORIGIN6("OriginOS 6")
}

class Pal(val dark: Boolean) {
    val bg = if (dark) Color.Black else Color(0xFFF0F1F3)
    val card = if (dark) Color(0xFF1A1A1A) else Color.White
    val text = if (dark) Color.White else Color(0xFF111111)
    val sub = if (dark) Color(0xFF9A9A9A) else Color(0xFF86868B)
    val line = if (dark) Color(0x22FFFFFF) else Color(0x14000000)
    val track = if (dark) Color(0xFF2E2E2E) else Color(0xFFE2E3E6)
}

@Composable
fun Panel(p: Pal, m: Modifier = Modifier, h: Dp = 24.dp, v: Dp = 8.dp, content: @Composable ColumnScope.() -> Unit) {
    Column(m.clip(RoundedCornerShape(26.dp)).background(p.card).padding(horizontal = h, vertical = v), content = content)
}

@Composable fun Line(p: Pal) = Box(Modifier.fillMaxWidth().height(0.6.dp).background(p.line))

@Composable
fun StorageBar(frac: Float, p: Pal, h: Int = 14) {
    val segs = listOf(
        0.40f to Color(0xFF3B4EF5), 0.07f to Color(0xFF3E8BF0), 0.15f to Color(0xFF6B1FCB),
        0.012f to Color(0xFF2EBD59), 0.012f to Color(0xFFF5C518), 0.012f to Color(0xFFF08A24),
        0.012f to Color(0xFFE0452B), 0.08f to Color(0xFF8E93B5), 0.05f to Color(0xFFBCC0DA)
    )
    val sum = segs.map { it.first }.sum()
    Canvas(Modifier.fillMaxWidth().height(h.dp).clip(RoundedCornerShape(50))) {
        drawRect(p.track)
        var x = 0f
        val usedW = size.width * frac.coerceIn(0.03f, 1f)
        segs.forEach { (w, c) ->
            val sw = usedW * w / sum
            drawRect(c, Offset(x, 0f), Size(sw, size.height)); x += sw
        }
    }
}

@Composable
fun TopBar(title: String, p: Pal, circle: Boolean, menu: Boolean) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(46.dp).then(if (circle) Modifier.clip(CircleShape).background(p.card) else Modifier),
            contentAlignment = Alignment.Center
        ) { Text("←", color = p.text, fontSize = 24.sp) }
        Spacer(Modifier.width(14.dp))
        Text(title, color = p.text, fontSize = 22.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.weight(1f))
        if (menu) Text("⋮", color = p.text, fontSize = 26.sp, modifier = Modifier.padding(end = 8.dp))
    }
}

@Composable
fun KV(label: String, value: String, p: Pal, chip: String? = null) {
    Row(Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = p.text, fontSize = 19.sp, fontWeight = FontWeight.Medium)
        Row(Modifier.weight(1f).padding(start = 12.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Text(value, color = p.sub, fontSize = 16.sp, textAlign = TextAlign.End, modifier = Modifier.weight(1f, false))
            if (chip != null) {
                Spacer(Modifier.width(6.dp))
                Box(Modifier.border(1.2.dp, p.sub, RoundedCornerShape(7.dp)).padding(horizontal = 7.dp, vertical = 1.dp)) {
                    Text(chip, color = p.sub, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PhoneIcon(c: Color) = Box(Modifier.size(22.dp, 34.dp).border(2.dp, c, RoundedCornerShape(5.dp)))

@Composable
fun ChevCircle(fg: Color, bg: Color) = Box(
    Modifier.size(20.dp).clip(CircleShape).background(bg), contentAlignment = Alignment.Center
) { Text("›", color = fg, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
