package com.example.dailyquotewidget1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlin.random.Random

class QuoteWidget : AppWidgetProvider() {

    companion object {
        private val quotes = listOf(
            "Believe you can and you're halfway there. - Theodore Roosevelt",
            "Do what you can, with what you have, where you are. - Theodore Roosevelt",
            "Act as if what you do makes a difference. It does. - William James",
            "Keep your face always toward the sunshine—and shadows will fall behind you. - Walt Whitman",
            "The only limit to our realization of tomorrow is our doubts of today. - Franklin D. Roosevelt",
            "It does not matter how slowly you go as long as you do not stop. - Confucius",
            "Don't watch the clock; do what it does. Keep going. - Sam Levenson",
            "With the new day comes new strength and new thoughts. - Eleanor Roosevelt",
            "It always seems impossible until it's done. - Nelson Mandela",
            "Quality means doing it right when no one is looking. - Henry Ford",
            "The best way to predict the future is to create it. - Peter Drucker",
            "We become what we think about. - Earl Nightingale",
            "I have not failed. I've just found 10,000 ways that won't work. - Thomas Edison",
            "You are never too old to set another goal or to dream a new dream. - C.S. Lewis",
            "A journey of a thousand miles begins with a single step. - Lao Tzu",
            "Success is not final, failure is not fatal: It is the courage to continue that counts. - Winston Churchill",
            "Happiness depends upon ourselves. - Aristotle",
            "The only way to do great work is to love what you do. - Steve Jobs",
            "What lies behind us and what lies before us are tiny matters compared to what lies within us. - Ralph Waldo Emerson",
            "If you want to lift yourself up, lift up someone else. - Booker T. Washington"
        )

        fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Pick a new random quote
            val quote = quotes[Random.nextInt(quotes.size)]
            val parts = quote.split(" - ")
            val quoteText = parts[0]
            val authorText = if (parts.size > 1) "- ${parts[1]}" else ""

            // Update UI
            views.setTextViewText(R.id.quoteText, quoteText)
            views.setTextViewText(R.id.authorText, authorText)

            // Create an Intent to update widget
            val intent = Intent(context, QuoteWidget::class.java)
            intent.action = "com.example.dailyquotewidget1.UPDATE_WIDGET"
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            val pendingIntent = PendingIntent.getBroadcast(
                context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            views.setOnClickPendingIntent(R.id.widgetContainer, pendingIntent)

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "com.example.dailyquotewidget1.UPDATE_WIDGET") {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}
