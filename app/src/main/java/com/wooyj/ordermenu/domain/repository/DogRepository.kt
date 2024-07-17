package com.wooyj.ordermenu.domain.repository

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO

interface DogRepository {
    suspend fun getBreedList(): List<BreedListDTO>
}

// OOP : Kotlin, Java, Swift, C++... ETC

// Class -> 3가지 특징
// 1. 상속성(Inheritance) -> 확장
// 2. 다형성(Polymorphism) -> 오버로딩, 오버라이딩
// 3. 캡슐화(Encapsulation) -> 정보은닉

// Class -> SOLID

// S : SRP(Single Responsibility Principle) 단일 책임 원칙
// O : Open(확장)-Closed(수정) Principle 개방 폐쇄 원칙
// L : Liskov Substitution Principle 리스코프 치환 원칙 -> 상속, 다형성
// I : Interface Segregation Principle 인터페이스 분리 원칙
// D : Dependency Inversion Principle 의존성 역 -> Hilt