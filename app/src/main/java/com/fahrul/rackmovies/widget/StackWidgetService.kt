package com.fahrul.rackmovies.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.fahrul.rackmovies.widget.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}