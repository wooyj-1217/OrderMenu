package com.wooyj.ordermenu.domain.usecase

import com.wooyj.ordermenu.domain.repository.DogRepository
import dagger.Reusable
import javax.inject.Inject

// Lazy vs Reusable

// Lazy -> 없으면 만들고, 있으면 준다
// Reusable -> 없으면 만들고, 있으면 준다, 안쓰면 버린다

@Reusable
class GetBreedListUseCase
@Inject
constructor(
    private val repository: DogRepository,
) {
    suspend operator fun invoke() = repository.getBreedList()
}
