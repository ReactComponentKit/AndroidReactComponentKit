package com.github.skyfe79.android.library.app.examples.counter.redux

import com.github.skyfe79.android.library.app.examples.counter.CounterState
import com.github.skyfe79.android.library.app.examples.counter.CounterViewModel
import com.github.skyfe79.android.library.app.examples.counter.CounterViewModel2
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.redux.Action


//목표는 리듀서와 액션을 없애는 것이다.
//단 주의할 점은 RCK는 상태 mutation을 모두 백그라운드에서 실행하고 있다.
//과연 이것이 필요한 것일까?
//async 만 백그라운드에서 실행하면 되지 않을까?
fun CounterViewModel.countReducer(action: Action) = setState { state ->
    when(action) {
        is IncreaseAction -> increment(action.payload)
        is DecreaseAction -> decrement(action.payload)
        else -> state
    }
}

fun CounterViewModel2.countReducer(action: Action) = setState { state ->
    when(action) {
        is IncreaseAction -> setState {
            with(it) { copy(count = count + action.payload) }
        }
        is DecreaseAction -> setState {
            with(it) { copy(count = count - action.payload) }
        }
        else -> state
    }
}


// 이렇게 정의한 리듀서를 액션 없이 각 컴포넌트에서 사용할 수 있어야 한다.
// 각 컴포넌트는 ViewModel을 받는다. 그리고 dispatch wrapper 함수로 감싼다.
fun CounterViewModel.increment(payload: Int) = setState {
    with(it) { copy(count = count + payload) }
    // 여기 내부에서 호출할 수 있는 다른 메서드는 오직 리듀서 뿐이다.
    // decrement(payload)
    // async를 어떻게 할 것인가?
    /*
    async {
        increment(payload)
    }
     */
}

fun CounterViewModel.decrement(payload: Int) = setState {
    with(it) { copy(count = count - payload) }
}