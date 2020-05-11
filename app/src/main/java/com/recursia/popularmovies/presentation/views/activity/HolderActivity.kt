package com.recursia.popularmovies.presentation.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.recursia.popularmovies.R
import com.recursia.popularmovies.Screens
import com.recursia.popularmovies.TheApplication
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class HolderActivity : AppCompatActivity() {
    private val navigator: Navigator = object : SupportAppNavigator(this, R.id.main_container) {
        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }
    private lateinit var navigatorHolder: NavigatorHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)
        navigatorHolder = TheApplication
                .getInstance()
                .appComponent
                .navigationHolder
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.IntroScreen())))
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
