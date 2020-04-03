package com.fahrul.rackmovies.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.fahrul.rackmovies.adapter.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}