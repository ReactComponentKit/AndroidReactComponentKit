package com.github.skyfe79.android.reactcomponentkit.component

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.collectionview.SectionContent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView

abstract class ViewComponent: AnkoComponent<Context>, ReactComponent {
    override var token: Token
    private var cachedView: View? = null

    constructor(token: Token) {
        this.token = token
    }

    override fun createView(ui: AnkoContext<Context>): View {
        val view = cachedView ?: layout(ui)
        cachedView = view
        return view
    }

    /**
     * Configure component's ui at here
     */
    abstract fun layout(ui: AnkoContext<Context>): View

    /**
     * It is called when the component is standalone.
     */
    open fun on(state: State) = Unit

    /**
     * It is only called when the component is in RecyclerView's row
     */
    open fun on(item: ItemModel, position: Int) = Unit

    /**
     * It is only called when the component is in CollectionView's row
     */
    open fun on(content: SectionContent, position: Int) = Unit
}

/**
 * You can ViewComponents in Anko DSL with below extensions.
 * example:
 * verticalLayout {
 *  component(MyViewComponent(...))
 * }
 */
fun ViewManager.component(component: ViewComponent, theme: Int = 0): View {
    return component(component, theme) {}
}

inline fun ViewManager.component(component: ViewComponent, theme: Int = 0, init: View.() -> Unit = {}): View {
    return ankoView({
        component.createView(AnkoContext.create(it))
    }, theme = theme, init = init)
}