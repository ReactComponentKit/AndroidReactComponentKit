package com.github.skyfe79.android.library.app.component

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.skyfe79.android.library.app.MainState
import com.github.skyfe79.android.reactcomponentkit.component.FragmentComponent
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class LoadingComponent: FragmentComponent() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            relativeLayout {
                backgroundColor = ContextCompat.getColor(this.context, android.R.color.transparent)
                horizontalProgressBar().lparams {
                    centerInParent()
                }

                progressBar().lparams {
                    centerInParent()
                }
            }
        }.view
    }

    override fun on(state: State) {
        val newState = (state as? MainState) ?: return
        Log.v("NEW_STATE", "$newState")
    }
}