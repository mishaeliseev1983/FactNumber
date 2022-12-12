package com.melyseev.factnumber.di.modules

import android.content.Context
import com.melyseev.factnumber.di.ApplicationScope
import com.melyseev.factnumber.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @ApplicationScope
    @Provides
    fun provideApplication(app: Context): App {
        return app as App
    }
}