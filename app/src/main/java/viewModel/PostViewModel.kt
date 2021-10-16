package viewModel;

import androidx.lifecycle.ViewModel
import repository.PostRepository
import repository.PostRepositoryInMemoryImpl
import androidx.lifecycle.MutableLiveData

class PostViewModel : ViewModel(){
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
}
