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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/* ---------------- ColorOS 17 ---------------- */
@Composable
fun Os17Hero(name: String, ver: String, status: String, m: Modifier = Modifier) {
    Box(m.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(30.dp)), contentAlignment = Alignment.Center) {
        Canvas(Modifier.matchParentSize()) {
            val w = size.width; val h = size.height
            drawRect(Brush.verticalGradient(listOf(Color(0xFF1F1D52), Color(0xFF3E3AA0), Color(0xFF9B83DA), Color(0xFFB98BE0))))
            val wedge = Path().apply { moveTo(0f, h * .30f); lineTo(w, h * .80f); lineTo(0f, h * .80f); close() }
            drawPath(wedge, Color(0xFF29265E))
            val c = Offset(w * .78f, h * .42f)
            drawCircle(Brush.radialGradient(listOf(Color(0xFFE8602C), Color(0xFFE0807A), Color(0xFF9A73CB)), center = Offset(w * .8f, h * .5f), radius = w * .48f), radius = w * .45f, center = c)
            drawCircle(Color.White.copy(alpha = .18f), radius = w * .45f, center = c, style = Stroke(3f))
            drawCircle(Brush.radialGradient(listOf(Color(0xFFEF6A38), Color(0xFFEF8A55)), center = Offset(w * .2f, h * 1.02f), radius = w * .45f), radius = w * .45f, center = Offset(w * .2f, h * 1.05f))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(name, color = Color.White.copy(alpha = .95f), fontSize = 56.sp, fontWeight = FontWeight.Medium)
            Text(ver, color = Color.White.copy(alpha = .9f), fontSize = 17.sp)
        }
        Box(Modifier.align(Alignment.BottomCenter).padding(bottom = 60.dp).clip(RoundedCornerShape(50)).background(Color.White.copy(alpha = .22f)).padding(horizontal = 26.dp, vertical = 12.dp)) {
            Text(status, color = Color.White, fontSize = 17.sp)
        }
    }
}

@Composable
fun ColorOS17Screen(s: Specs, p: Pal) {
    Column(Modifier.fillMaxSize().background(p.bg).statusBarsPadding().verticalScroll(rememberScrollState()).padding(bottom = 96.dp)) {
        TopBar("About device", p, true, false)
        Os17Hero("ColorOS", "17.0", "Version up to date", Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
        Spacer(Modifier.height(14.dp))
        Panel(p, Modifier.padding(horizontal = 16.dp)) {
            Row(Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                Text("Device name", color = p.text, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.weight(1f)); Text(s.deviceName + "  ›", color = p.sub, fontSize = 17.sp)
            }
            Line(p)
            Row(Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text("Storage", color = p.text, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.weight(1f)); Text("${s.used} / ${s.total}  ›", color = p.sub, fontSize = 17.sp)
            }
            Spacer(Modifier.height(10.dp)); StorageBar(s.usedFrac, p); Spacer(Modifier.height(18.dp))
        }
        Spacer(Modifier.height(14.dp))
        Panel(p, Modifier.padding(horizontal = 16.dp), v = 20.dp) {
            Text(s.deviceName, color = p.text, fontSize = 22.sp, fontWeight = FontWeight.Medium)
            Text(s.model + "_17.0.0.102", color = p.sub, fontSize = 16.sp)
            Spacer(Modifier.height(16.dp)); Line(p); Spacer(Modifier.height(16.dp))
            Stack("Processor", s.soc, p)
            Stack("RAM  ›", s.ram + (s.ramExt?.let { " + $it" } ?: ""), p)
            Stack("Cameras  ›", "Front ${s.front}\nRear ${s.rear}", p)
            Stack("Battery", s.battery, p)
            Stack("Display", s.display, p)
            Stack("Android version", s.android, p)
            Stack("Security update", s.patch, p)
        }
    }
}

@Composable
fun Stack(label: String, value: String, p: Pal) {
    Column(Modifier.fillMaxWidth().padding(bottom = 18.dp)) {
        Text(label, color = p.sub, fontSize = 17.sp)
        Text(value, color = p.text, fontSize = 20.sp, fontWeight = FontWeight.Medium)
    }
}

/* ---------------- ColorOS 15 / OxygenOS 15 / realme UI 7 ---------------- */
@Composable
fun BrandHero(skin: Skin, p: Pal, s: Specs, m: Modifier = Modifier) {
    val dark = p.dark
    val tall = skin == Skin.REALME7
    Box(m.fillMaxWidth().height(if (tall) 400.dp else 250.dp).clip(RoundedCornerShape(30.dp))) {
        Canvas(Modifier.matchParentSize()) {
            val w = size.width; val h = size.height
            when (skin) {
                Skin.COLOROS15 -> drawRect(Brush.verticalGradient(if (dark) listOf(Color(0xFF4A3220), Color(0xFF2B2C31), Color(0xFF2A2E35)) else listOf(Color(0xFFE9B999), Color(0xFFBBC1CA), Color(0xFFB2BDCC))))
                Skin.OXYGEN15 -> drawRect(Brush.verticalGradient(if (dark) listOf(Color(0xFF33383C), Color(0xFF4A4244), Color(0xFFB5766A)) else listOf(Color(0xFFD7DFEA), Color(0xFFF8F1EF), Color(0xFFFF6A4A))))
                else -> {
                    drawRect(Color(0xFFB9C4DB))
                    drawRect(Brush.radialGradient(listOf(Color(0xFFF6D12C), Color(0x00F6D12C)), center = Offset(w * .1f, h * .8f), radius = w * .9f))
                    drawRect(Brush.radialGradient(listOf(Color(0xFF2554D8), Color(0x002554D8)), center = Offset(w * .88f, h * .55f), radius = w * .65f))
                    drawRect(Brush.radialGradient(listOf(Color(0x889C8CE0), Color(0x009C8CE0)), center = Offset(w * .5f, h * .45f), radius = w * .3f))
                }
            }
        }
        val fg = if (skin == Skin.OXYGEN15 && !dark) Color(0xFF111111) else Color.White
        Column(Modifier.align(Alignment.TopCenter).padding(top = if (tall) 56.dp else 44.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            when (skin) {
                Skin.COLOROS15 -> { Text("oppo", color = Color.White.copy(alpha = .9f), fontSize = 30.sp, fontWeight = FontWeight.Medium); Spacer(Modifier.height(10.dp)) }
                Skin.OXYGEN15 -> { Box(Modifier.size(34.dp).border(3.dp, fg, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) { Text("1", color = fg, fontSize = 18.sp, fontWeight = FontWeight.Bold) }; Spacer(Modifier.height(12.dp)) }
                else -> {}
            }
            if (!tall) Text(s.deviceName, color = fg, fontSize = 38.sp, fontWeight = FontWeight.Bold)
            else Text(s.deviceName, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        if (tall) {
            Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("realme UI", color = Color.White.copy(alpha = .85f), fontSize = 62.sp, fontWeight = FontWeight.SemiBold)
                Text("7.0", color = Color.White, fontSize = 20.sp)
            }
            Text("Version up to date", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 56.dp))
        } else {
            Row(Modifier.align(Alignment.BottomCenter).padding(bottom = 26.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(if (skin == Skin.COLOROS15) "ColorOS 15.0" else "OxygenOS 15.0", color = fg, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.width(8.dp))
                ChevCircle(if (fg == Color.White) Color(0xFF333333) else Color.White, if (fg == Color.White) Color.White else Color(0xFF222222))
            }
        }
    }
}

@Composable
fun CardsScreen(skin: Skin, s: Specs, p: Pal) {
    Column(Modifier.fillMaxSize().background(p.bg).statusBarsPadding().verticalScroll(rememberScrollState()).padding(bottom = 96.dp)) {
        TopBar("About device", p, false, true)
        BrandHero(skin, p, s, Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(12.dp))
        Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Panel(p, Modifier.weight(1f).height(160.dp), 20.dp, 20.dp) {
                PhoneIcon(p.text); Spacer(Modifier.weight(1f))
                Text("Device name", color = p.sub, fontSize = 17.sp)
                Text(s.deviceName, color = p.text, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            }
            Panel(p, Modifier.weight(1f).height(160.dp), 20.dp, 20.dp) {
                Spacer(Modifier.height(6.dp)); StorageBar(s.usedFrac, p, 12); Spacer(Modifier.weight(1f))
                Text("Storage", color = p.sub, fontSize = 17.sp)
                Text("${s.used} / ${s.total}", color = p.text, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            }
        }
        Spacer(Modifier.height(12.dp))
        Panel(p, Modifier.padding(horizontal = 16.dp)) {
            KV("Processor", s.soc, p); Line(p)
            KV("Battery", s.battery, p); Line(p)
            KV("RAM", s.ram, p, s.ramExt); Line(p)
            KV("Cameras", "Front ${s.front}\nRear ${s.rear}", p); Line(p)
            KV("Display", s.display, p); Line(p)
            KV("Model", s.model, p); Line(p)
            KV("Android version", s.android, p); Line(p)
            KV("Security update", s.patch, p); Line(p)
            KV("Build number", s.build, p)
        }
    }
}

/* ---------------- HyperOS 4 ---------------- */
@Composable
fun HyperOSScreen(s: Specs, p: Pal) {
    val d = p.dark
    val bg = if (d) listOf(Color(0xFF182470), Color(0xFF151B58), Color(0xFF2F1B4C)) else listOf(Color(0xFFE2DCFB), Color(0xFFF2DCEB), Color(0xFFE3E0FA))
    val tc = if (d) listOf(Color(0xFF6F8BE8), Color(0xFFB67FE0)) else listOf(Color(0xFF8E2E6E), Color(0xFF6B3E9E))
    val card = if (d) Color(0x30FFFFFF) else Color(0xCCFFFFFF)
    val tx = if (d) Color.White else Color(0xFF111111)
    val sb = if (d) Color(0xFFB0B4D0) else Color(0xFF86868B)
    Column(Modifier.fillMaxSize().background(Brush.verticalGradient(bg)).statusBarsPadding().verticalScroll(rememberScrollState()).padding(bottom = 96.dp)) {
        Text("‹", color = tx, fontSize = 40.sp, modifier = Modifier.padding(start = 24.dp, top = 12.dp))
        Spacer(Modifier.height(190.dp))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Xiaomi HyperOS", style = TextStyle(brush = Brush.horizontalGradient(tc), fontSize = 42.sp, fontWeight = FontWeight.Medium))
            Spacer(Modifier.height(8.dp))
            Text("4.0.0.0", color = sb, fontSize = 22.sp)
        }
        Spacer(Modifier.height(190.dp))
        Column(Modifier.padding(horizontal = 16.dp).clip(RoundedCornerShape(30.dp)).background(card).padding(horizontal = 24.dp, vertical = 12.dp)) {
            HRow("Device name", s.deviceName + "  ›", tx, sb)
            HRow("Storage", "${s.used} / ${s.total}  ›", tx, sb)
            HRow("OS version", s.build, tx, sb)
        }
        Spacer(Modifier.height(14.dp))
        Column(Modifier.padding(horizontal = 16.dp).clip(RoundedCornerShape(30.dp)).background(card).padding(24.dp)) {
            Text(s.deviceName, color = tx, fontSize = 34.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(22.dp))
            HStack(s.soc, "CPU", tx, sb)
            HStack(s.ram + (s.ramExt?.let { " + $it" } ?: ""), "RAM", tx, sb)
            HStack(s.battery, "Battery", tx, sb)
            HStack("Front ${s.front} / Rear ${s.rear}", "Cameras", tx, sb)
            HStack(s.display, "Display", tx, sb)
            HStack(s.android + " (${s.patch})", "Android version", tx, sb)
        }
    }
}

@Composable
fun HRow(l: String, v: String, tx: Color, sb: Color) {
    Row(Modifier.fillMaxWidth().padding(vertical = 18.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(l, color = tx, fontSize = 21.sp, fontWeight = FontWeight.Medium)
        Text(v, color = sb, fontSize = 17.sp, textAlign = TextAlign.End, modifier = Modifier.weight(1f).padding(start = 12.dp))
    }
}

@Composable
fun HStack(v: String, l: String, tx: Color, sb: Color) {
    Column(Modifier.padding(bottom = 18.dp)) {
        Text(v, color = tx, fontSize = 21.sp, fontWeight = FontWeight.Medium)
        Text(l, color = sb, fontSize = 16.sp)
    }
}

/* ---------------- OriginOS 6 ---------------- */
@Composable
fun OriginScreen(s: Specs, p: Pal) {
    Column(Modifier.fillMaxSize().background(if (p.dark) Color(0xFF0E0E10) else Color(0xFFF6F6F8)).statusBarsPadding().verticalScroll(rememberScrollState()).padding(bottom = 96.dp)) {
        TopBar("About phone", p, false, false)
        Box(Modifier.padding(horizontal = 18.dp).fillMaxWidth().height(210.dp).clip(RoundedCornerShape(22.dp)), contentAlignment = Alignment.Center) {
            Canvas(Modifier.matchParentSize()) {
                val w = size.width; val h = size.height
                drawRect(Brush.linearGradient(listOf(Color(0xFFC8400A), Color(0xFFE8841A)), Offset(0f, 0f), Offset(w, h * .6f)))
                drawCircle(Brush.radialGradient(listOf(Color(0xFFF7B955), Color(0x00F7B955)), center = Offset(w * .8f, h * .3f), radius = w * .5f), radius = w * .5f, center = Offset(w * .8f, h * .3f))
                val pink = Path().apply { moveTo(0f, h * .78f); lineTo(w * .65f, h); lineTo(0f, h); close() }
                drawPath(pink, Brush.horizontalGradient(listOf(Color(0xFFB0309E), Color(0xFFF03FA6))))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("origin", color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.Bold)
                Text("OS", color = Color.White.copy(alpha = .85f), fontSize = 52.sp, fontWeight = FontWeight.Light)
                Text(" 6", color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(30.dp))
        Text(s.deviceName, color = p.text, fontSize = 36.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(Modifier.height(18.dp))
        Box(Modifier.padding(horizontal = 24.dp)) { Line(p) }
        Spacer(Modifier.height(20.dp))
        OItem("Device name", s.deviceName, p)
        OItem("Processor", s.soc, p)
        Row { Box(Modifier.weight(1f)) { OItem("Phone storage", s.total, p) }; Box(Modifier.weight(1f)) { OItem("RAM", s.ram, p) } }
        if (s.ramExt != null) OItem("RAM extension", s.ramExt, p)
        OItem("Used storage", s.used, p)
        OItem("Cameras", "Front ${s.front} / Rear ${s.rear}", p)
        OItem("Battery capacity", s.battery.replace(" (TYP)", ""), p)
        OItem("Display", s.display, p)
        OItem("Android version", s.android, p)
        OItem("Security patch", s.patch, p)
        OItem("Build number", s.build, p)
    }
}

@Composable
fun OItem(label: String, value: String, p: Pal) {
    Column(Modifier.padding(horizontal = 24.dp).padding(bottom = 24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(3.dp, 12.dp).background(Color(0xFFE53935)))
            Spacer(Modifier.width(8.dp))
            Text(label, color = p.sub, fontSize = 16.sp)
        }
        Text(value, color = p.text, fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 11.dp, top = 2.dp))
    }
}
