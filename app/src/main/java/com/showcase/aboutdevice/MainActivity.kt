package com.showcase.aboutdevice

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val specs = SpecReader.read(this)
        setContent { App(specs) }
    }
}

@Composable
fun App(s: Specs) {
    var dark by remember { mutableStateOf(false) }
    val skins = Skin.values()
    val pager = rememberPagerState { skins.size }
    val scope = rememberCoroutineScope()
    val view = LocalView.current
    SideEffect {
        val w = (view.context as Activity).window
        val c = WindowCompat.getInsetsController(w, view)
        c.isAppearanceLightStatusBars = !dark
        c.isAppearanceLightNavigationBars = !dark
    }
    val p = Pal(dark)
    Box(Modifier.fillMaxSize().background(p.bg)) {
        HorizontalPager(pager, Modifier.fillMaxSize()) { page ->
            when (skins[page]) {
                Skin.COLOROS17 -> ColorOS17Screen(s, p)
                Skin.COLOROS16, Skin.COLOROS15 -> OppoIdScreen(skins[page], s, p)
                Skin.OXYGEN15, Skin.REALME7 -> CardsScreen(skins[page], s, p)
                Skin.HYPEROS4 -> HyperOSScreen(s, p)
                Skin.ORIGIN6 -> OriginScreen(s, p)
            }
        }
        Row(
            Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = 8.dp)
                .clip(RoundedCornerShape(50)).background(Color(0xDD1E1E1E)).padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val cur = pager.currentPage
            Text("‹", color = Color.White, fontSize = 24.sp, modifier = Modifier.clickable {
                scope.launch { pager.animateScrollToPage((cur - 1 + skins.size) % skins.size) }
            }.padding(horizontal = 14.dp, vertical = 6.dp))
            Text(skins[cur].label, color = Color.White, fontSize = 14.sp)
            Text("›", color = Color.White, fontSize = 24.sp, modifier = Modifier.clickable {
                scope.launch { pager.animateScrollToPage((cur + 1) % skins.size) }
            }.padding(horizontal = 14.dp, vertical = 6.dp))
            Text(if (dark) "☀" else "☾", color = Color.White, fontSize = 18.sp, modifier = Modifier.clickable {
                dark = !dark
            }.padding(horizontal = 14.dp, vertical = 6.dp))
        }
    }
}
