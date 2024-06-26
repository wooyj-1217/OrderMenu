package com.wooyj.ordermenu.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val preferencesDataStoreFileName = "settings"

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        preferencesDataStoreFileName,
    )

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.preferencesDataStore
    // TODO("protobuf 관련 질문입니다")
    // 회사에서 현재 protobuffer를 서버에서 VO로 사용하고 있고,
    // 저희쪽에서도 데이터를 받아올 때 서버에서 제공하는 .proto 객체를 빌드해서 받아오고 있습니다.
    // 히스토리를 들으니 전 본부장이 protobuffer를 강력하게 추진하셔서 이렇게 되었다고 하는데..
    // 일반적이진 않아서 왜 도입한거냐고 그 당시 본부장에게 여쭤봤더니 '보안에 좋아서' 라고 하셨습니다.(현재 잘림)
    // 서버에서 전달해주는 값을 보면... 단순히 base64로 변환된 string 값을 전달해주는데 어째서 이게 보안에 좋은거지..? 라고 의아했던 기억이 납니다.
    // 1. 보안상의 이유로 protobuffer를 사용한다는게 맞는 말인가요?
    // 2. gRPC를 회사에서 현재 쓰고있지는 않은데 혹시 써야되는 이유가 있나요?
}
