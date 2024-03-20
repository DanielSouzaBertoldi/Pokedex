package daniel.bertoldi.pokedex.di

import android.content.Context
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import daniel.bertoldi.pokedex.GenerationsSettingsSerializer
import javax.inject.Singleton

private val Context.dataStore by dataStore(
    fileName = "generations_settings",
    serializer = GenerationsSettingsSerializer,
)

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideGenerationsDataStore(@ApplicationContext appContext: Context) = appContext.dataStore
}