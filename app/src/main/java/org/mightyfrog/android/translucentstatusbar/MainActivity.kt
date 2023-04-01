package org.mightyfrog.android.translucentstatusbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import org.mightyfrog.android.translucentstatusbar.ui.theme.TranslucentStatusBarTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TranslucentStatusBarTheme {
                MyList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyList(
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState),
) {
    val isTopAppBarVisible by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction != 1f }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Translucent Status Bar Sample")
                },
                scrollBehavior = scrollBehavior,
                modifier = if (isTopAppBarVisible) Modifier else Modifier.statusBarsPadding(),
            )
        }
    ) { contentPadding ->
        val rows = 1..100
        LazyColumn(
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(rows.toList()) {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "$it",
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    }
}