package com.djk.opener.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djk.opener.models.BlockedApp
import com.djk.opener.viewmodels.BlockedAppsViewModel
import com.djk.opener.viewmodels.BlockAttemptsViewModel

class ParentDashboardActivity : ComponentActivity() {
    private val blockedAppsViewModel: BlockedAppsViewModel by viewModels {
        BlockedAppsViewModel.Factory(application)
    }

    private val blockAttemptsViewModel: BlockAttemptsViewModel by viewModels {
        BlockAttemptsViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParentDashboard(
                blockedAppsViewModel = blockedAppsViewModel,
                blockAttemptsViewModel = blockAttemptsViewModel
            )
        }
    }
}

@Composable
fun ParentDashboard(
    blockedAppsViewModel: BlockedAppsViewModel,
    blockAttemptsViewModel: BlockAttemptsViewModel
) {
    val blockedApps by blockedAppsViewModel.blockedApps.collectAsState(emptyList())
    val blockedCount by blockedAppsViewModel.blockedAppsCount.collectAsState(0)
    val recentAttempts by blockAttemptsViewModel.recentAttempts.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard Parent",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Statistiques
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$blockedCount",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Apps Bloquées",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Liste des apps bloquées
        Text(
            text = "Apps Bloquées",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 24.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(blockedApps) { app ->
                AppBlockCard(app)
            }
        }
    }
}

@Composable
fun AppBlockCard(app: BlockedApp) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = app.appName,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = app.packageName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = app.blockReason,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
