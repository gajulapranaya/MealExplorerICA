package com.sample.mealexplorer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.sample.mealzapp.R

@Composable
fun ProgressErrorView(
    isProgress: Boolean = false,
    message: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if (isProgress) {
            CircularProgressIndicator(
                modifier = Modifier.size(dimensionResource(R.dimen.progress_bar_size))
            )
        } else {
            Text(
                text = message,
                modifier = Modifier.padding(dimensionResource(R.dimen.error_text_padding)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )

        }

    }
}