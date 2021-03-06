package com.task.browse.domain

import com.task.model.Comic
import com.task.remote.data.Resource
import com.task.remote.data.Resource.Status.ERROR
import com.task.remote.data.Resource.Status.SUCCESS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

// useCase to get last comic from remote merged with local comics without duplication
class GetComicsUseCase(private val repository: ComicRepository) {
    operator fun invoke(): Flow<Resource<List<Comic>>> {
        return repository.getAllComics().zip(repository.getLastComic()) { f1, f2 ->
            if (f1.status == SUCCESS && f2.status == SUCCESS) {
                f1.data?.findLast { it.num == f2.data?.num }?.let {
                    Resource.success(f1.data)
                } ?: kotlin.run {
                    if (f1.data?.isNullOrEmpty() == true) {
                        Resource.success(data = listOf(f2.data!!))
                    } else {
                        f1.data?.toMutableList()?.add(0, f2.data!!)
                        Resource.success(f1.data)
                    }
                }
            } else if (f2.status == ERROR && f1.status == SUCCESS)
                Resource.success(data = f1.data)
            else f1
        }
    }
}
