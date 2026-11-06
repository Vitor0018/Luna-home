package br.com.luna.casa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LunaScreen() }
    }
}

@Composable
fun LunaScreen() {
    val relayStates = remember { mutableStateListOf(*Array(8) { false }) }
    var selectedTab by remember { mutableIntStateOf(0) }
    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(topBar = { Surface { Text("Luna • Casa inteligente", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.titleLarge) } },
            bottomBar = { NavigationBar {
                NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0 }, label = { Text("Relés") }, icon = { Text("⌂") })
                NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, label = { Text("Sensores") }, icon = { Text("◉") })
                NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2 }, label = { Text("Ajustes") }, icon = { Text("⚙") })
            } }) { padding ->
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                Text("Modo demonstração — ESP32 não conectado", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(18.dp))
                when (selectedTab) {
                    0 -> LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items((1..8).toList()) { n ->
                            ElevatedCard { Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Relé $n")
                                Switch(checked = relayStates[n - 1], onCheckedChange = { relayStates[n - 1] = it })
                            } }
                        }
                    }
                    1 -> Text("DHT11 • Movimento • Chama • Buzzer\nAguardando conexão com ESP32.")
                    else -> Text("Luna v0.1 — APK de demonstração\nIntegração com ESP32 pendente.")
                }
            }
        }
    }
}
