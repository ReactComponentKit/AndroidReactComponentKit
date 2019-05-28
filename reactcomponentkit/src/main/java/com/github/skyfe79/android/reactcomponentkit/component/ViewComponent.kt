package com.github.skyfe79.android.reactcomponentkit.component

import android.content.Context
import android.view.View
import android.view.ViewManager
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.collectionview.SectionContent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import kotlin.reflect.KClass


abstract class ViewComponent(override var token: Token, override var receiveState: Boolean = true): AnkoComponent<Context>, ReactComponent {

    override var newStateEventBus: EventBus<ComponentNewStateEvent>? = if (receiveState) EventBus(token) else null
    override var dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)

    init {
        newStateEventBus?.let {
            it.on { event ->
                when (event) {
                    is ComponentNewStateEvent.On -> on(event.state)
                }
            }
        }
    }

    private var cachedView: View? = null
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
inline fun ViewManager.component(component: ViewComponent, theme: Int = 0): View {
    return component(component, theme) {}
}

inline fun ViewManager.component(component: ViewComponent, theme: Int = 0, init: View.() -> Unit = {}): View {
    return ankoView({
        component.createView(AnkoContext.create(it))
    }, theme = theme, init = init)
}