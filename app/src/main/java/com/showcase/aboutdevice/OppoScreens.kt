package com.showcase.aboutdevice

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

private fun idn(v: String) = v.replace('.', ',')
private fun ram2(s: Specs) = idn(String.format(Locale.US, "%.2f GB", s.ram.substringBefore(' ').toDouble()))

@Composable
private fun Hero16(s: Specs, m: Modifier) {
    Box(m.fillMaxWidth().aspectRatio(1.16f).clip(RoundedCornerShape(22.dp))) {
        Canvas(Modifier.matchParentSize()) {
            val w = size.width; val h = size.height
            drawRect(Brush.verticalGradient(listOf(Color(0xFF2B1E78), Color(0xFF1A1040), Color(0xFF0A0610))))
            val bc = Offset(w * .84f, h * .36f)
            drawCircle(Brush.radialGradient(listOf(Color(0xFF7584F4), Color(0xFF2B3AA8), Color(0xFF14123D)), center = bc, radius = w * .42f), radius = w * .42f, center = bc)
            val rc = Offset(w * .30f, h * .58f)
            drawCircle(Brush.radialGradient(listOf(Color(0xFFE02A2A), Color(0xFF9E1230), Color(0xFF2A0A24)), center = Offset(w * .28f, h * .42f), radius = w * .55f), radius = w * .50f, center = rc)
            drawCircle(Color(0xFFF08AD8).copy(alpha = .9f), radius = w * .50f, center = rc, style = Stroke(w * .008f))
            drawCircle(Brush.radialGradient(listOf(Color(0x66E04AC0), Color(0x00E04AC0)), center = Offset(w * .45f, h * 1.02f), radius = w * .5f), radius = w * .5f, center = Offset(w * .45f, h * 1.02f))
        }
        Text(s.marketName, color = Color.White.copy(alpha = .85f), fontSize = 14.sp, modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp))
        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("ColorOS", color = Color.White.copy(alpha = .95f), fontSize = 38.sp, fontWeight = FontWeight.Medium)
            Text("16.0.2", color = Color.White.copy(alpha = .9f), fontSize = 14.sp)
        }
        Text("Versi sudah terbaru", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 46.dp))
    }
}

@Composable
private fun Hero15(s: Specs, dark: Boolean, m: Modifier) {
    Box(m.fillMaxWidth().aspectRatio(1.65f).clip(RoundedCornerShape(22.dp))) {
        Canvas(Modifier.matchParentSize()) {
            drawRect(Brush.verticalGradient(
                if (dark) listOf(Color(0xFF4A3220), Color(0xFF2B2C31), Color(0xFF2A2E35))
                else listOf(Color(0xFFE9A97A), Color(0xFFCDBFB5), Color(0xFFA9BCD0))
            ))
        }
        Column(Modifier.align(Alignment.TopCenter).padding(top = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("oppo", color = Color.White.copy(alpha = .8f), fontSize = 18.sp, fontWeight = FontWeight.Light)
            Spacer(Modifier.height(12.dp))
            Text(s.marketName, color = Color.White.copy(alpha = .92f), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
        Row(Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("ColorOS 15.0", color = Color.White.copy(alpha = .9f), fontSize = 12.sp)
            Spacer(Modifier.width(5.dp))
            ChevCircle(Color(0xFF9AA7B5), Color.White)
        }
    }
}

@Composable
private fun ORow(label: String, value: String?, tx: Color, sb: Color, chev: Boolean = false, chip: String? = null) {
    Row(Modifier.fillMaxWidth().padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = tx, fontSize = 16.sp, modifier = Modifier.weight(1f, false))
        Spacer(Modifier.weight(1f))
        if (value != null) Text(value, color = sb, fontSize = 14.sp, textAlign = TextAlign.End, modifier = Modifier.weight(1.3f, false).padding(start = 8.dp))
        if (chip != null) {
            Spacer(Modifier.width(5.dp))
            Box(Modifier.border(1.dp, sb, RoundedCornerShape(5.dp)).padding(horizontal = 5.dp)) {
                Text(chip, color = sb, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
        if (chev) Text("  ›", color = sb, fontSize = 18.sp)
    }
}

@Composable
fun OppoIdScreen(skin: Skin, s: Specs, p: Pal) {
    val d = p.dark
    val bg = if (d) Color.Black else Color(0xFFF2F3F5)
    val card = if (d) Color(0xFF1C1C1E) else Color(0xFFFBFBFC)
    val tx = if (d) Color.White else Color(0xFF1A1A1A)
    val sb = if (d) Color(0xFF8E8E93) else Color(0xFF8A8A8E)
    val ln = if (d) Color(0x1FFFFFFF) else Color(0x12000000)
    val is16 = skin == Skin.COLOROS16
    val pad = Modifier.padding(horizontal = 14.dp)
    val cardMod = pad.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(card).padding(horizontal = 16.dp, vertical = 2.dp)
    @Composable fun Ln() = Box(Modifier.fillMaxWidth().height(0.6.dp).background(ln))

    Column(Modifier.fillMaxSize().background(bg).statusBarsPadding().verticalScroll(rememberScrollState()).padding(bottom = 96.dp)) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("←", color = tx, fontSize = 22.sp)
            Spacer(Modifier.width(18.dp))
            Text("Tentang perangkat", color = tx, fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            Text("⋮", color = tx, fontSize = 22.sp)
        }
        if (is16) Hero16(s, pad) else Hero15(s, d, pad)
        Spacer(Modifier.height(10.dp))
        Row(pad, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(Modifier.weight(1f).height(118.dp).clip(RoundedCornerShape(18.dp)).background(card).padding(16.dp)) {
                Box(Modifier.size(13.dp, 21.dp).border(1.5.dp, tx, RoundedCornerShape(3.dp)))
                Spacer(Modifier.weight(1f))
                Text("Nama perangkat", color = sb, fontSize = 13.sp)
                Text(s.deviceName, color = tx, fontSize = 15.sp, fontWeight = FontWeight.Medium, maxLines = 2)
            }
            Column(Modifier.weight(1f).height(118.dp).clip(RoundedCornerShape(18.dp)).background(card).padding(16.dp)) {
                StorageBar(s.usedFrac, p, 8)
                Spacer(Modifier.weight(1f))
                Text("Penyimpanan", color = sb, fontSize = 13.sp)
                Text("${idn(s.used)} / ${idn(s.total)}", color = tx, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
        }
        Spacer(Modifier.height(10.dp))
        Column(cardMod) {
            ORow("Processor", s.soc, tx, sb); Ln()
            ORow("Baterai", s.battery.replace("TYP", "TIP"), tx, sb); Ln()
            ORow("RAM", ram2(s), tx, sb, chev = true, chip = s.ramExt?.let { idn(it) }); Ln()
            if (is16) { ORow("Versi Perangkat Lunak", s.build, tx, sb); Ln() }
            ORow("Peningkat performa", "Vitalisasi CPU", tx, sb, chev = true); Ln()
            ORow("Layar", idn(s.display).replace(" in", " inci").let { it.replace(",7 inci", ",7 inci") }, tx, sb); Ln()
            ORow("Model", s.model, tx, sb)
        }
        Spacer(Modifier.height(10.dp))
        Column(cardMod) {
            ORow("Versi", null, tx, sb, chev = true); Ln()
            ORow("Informasi hukum", null, tx, sb, chev = true); Ln()
            ORow("Status", null, tx, sb, chev = true); Ln()
            ORow("Panduan pengguna", null, tx, sb, chev = true)
        }
        Spacer(Modifier.height(10.dp))
        Column(cardMod) {
            ORow("Program peningkatan pengalaman", null, tx, sb, chev = true); Ln()
            ORow("Pengawasan", null, tx, sb, chev = true)
        }
    }
}
