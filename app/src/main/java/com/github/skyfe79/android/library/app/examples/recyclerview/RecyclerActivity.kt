package com.github.skyfe79.android.library.app.examples.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.skyfe79.android.library.app.R
import com.github.skyfe79.android.library.app.examples.recyclerview.component.ProfileViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.component.TextMessageViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessage
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        this.recyclerView.layoutManager = LinearLayoutManager(this)
        this.recyclerView.setHasFixedSize(true)

        adapter = RecyclerViewAdapter(token = Token.empty)
        adapter.register(TextMessageViewComponent::class)
        adapter.register(ProfileViewComponent::class)
        recyclerView.adapter = adapter

        adapter.set(arrayOf(
            TextMessage("안녕하세요1"),
            TextMessage("안녕하세요2"),
            TextMessage("안녕하세요3"),
            TextMessage("안녕하세요4"),
            TextMessage("안녕하세요5"),

            TextMessage("안녕하세요6"),
            TextMessage("안녕하세요7"),
            TextMessage("안녕하세요8"),
            TextMessage("안녕하세요9"),
            TextMessage("안녕하세요10"),

            TextMessage("안녕하세요11"),
            TextMessage("안녕하세요12"),
            TextMessage("안녕하세요13"),
            TextMessage("안녕하세요14"),
            TextMessage("안녕하세요15"),

            TextMessage("안녕하세요16"),
            TextMessage("안녕하세요17"),
            TextMessage("안녕하세요18"),
            TextMessage("안녕하세요19"),
            TextMessage("안녕하세요20")
        ))
    }
}