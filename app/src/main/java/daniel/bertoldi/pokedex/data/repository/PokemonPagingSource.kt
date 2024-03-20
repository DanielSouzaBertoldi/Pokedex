package daniel.bertoldi.pokedex.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.squareup.moshi.JsonDataException
import daniel.bertoldi.pokedex.presentation.mapper.PokemonBasicModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.PokemonBasicUiModel
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val pokemonBasicModelToUiModelMapper: PokemonBasicModelToUiModelMapper,
) : PagingSource<Int, PokemonBasicUiModel>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonBasicUiModel>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonBasicUiModel> {
        return try {
            val nextPageNumber = params.key ?: 1
            val pokemonModel = getPokemonUseCase.basicData(nextPageNumber)

            LoadResult.Page(
                data = listOf(pokemonBasicModelToUiModelMapper.mapFrom(pokemonModel)),
                prevKey = null,
                nextKey = pokemonModel.id.inc(),
//                nextKey = null,
            )
        } catch (e: JsonDataException) {
            Log.d("PAGING-LOAD-ERROR", e.toString())
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.d("PAGING-LOAD-ERROR", e.toString())
            // Handle error stuff and return LoadResult.Error if it's an expected error
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d("PAGING-LOAD-ERROR", e.toString())
            LoadResult.Error(e)
        }
    }
}