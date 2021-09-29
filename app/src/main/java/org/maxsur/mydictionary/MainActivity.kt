package org.maxsur.mydictionary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.maxsur.mydictionary.domain.interactor.DictionaryInteractor
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var interactor: DictionaryInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as DictionaryApplication).component.inject(this)
    }
}