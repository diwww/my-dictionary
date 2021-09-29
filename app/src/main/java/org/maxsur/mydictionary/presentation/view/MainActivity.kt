package org.maxsur.mydictionary.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.maxsur.mydictionary.DictionaryApplication
import org.maxsur.mydictionary.R
import org.maxsur.mydictionary.presentation.Screens
import javax.inject.Inject

/**
 * Главная активити приложения.
 */
class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this, R.id.container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as DictionaryApplication).component.inject(this)

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.dictionary())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}