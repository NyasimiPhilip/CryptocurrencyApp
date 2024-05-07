package com.enwai.cryptocurrencyapp.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.enwai.cryptocurrencyapp.domain.model.Coin

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (Coin) -> Unit
) {
    // Row composable representing a single item in the coin list
    Row(
        modifier = Modifier
            .fillMaxWidth() // Row takes the entire width of its parent
            .clickable { onItemClick(coin) } // Clickable behavior to handle item clicks
            .padding(20.dp) // Padding around the item
    ) {
        // Text displaying the coin's rank, name, and symbol
        Text(
            text = "${coin.rank} ${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyMedium, // Text style
            overflow = TextOverflow.Ellipsis // Handling text overflow with ellipsis
        )
        // Text displaying the coin's activity status (active or inactive)
        Text(
            text = if (coin.isActive) "active" else "inactive", // Conditional text based on activity status
            color = if (coin.isActive) Color.Green else Color.Red, // Text color based on activity status
            fontStyle = FontStyle.Italic, // Italic font style
            textAlign = TextAlign.End, // Text alignment to the end of the row
            style = MaterialTheme.typography.bodySmall, // Text style
            modifier = Modifier.weight(1f) // Weight to allow the text to take up remaining horizontal space
        )
    }
}
