Remote - Restful

OkHttp3     -> 반드시 있어야 한다
            -> Network 통신 담당
            -> Read, Write, Connection Time out
            -> Interceptor (Network info)

Retrofit2   -> okhttp 포함 되어있다.
            -> Network 통신을 쉽고 편하게
            -> TypeSafe
            -> Convert (String -> Gson, Moshi, KotlinXSer, Scal) -> Object
            -> CallAdapter(OkHttp Call -> Suspend fun )
                -> Call -> Type

Json polymorphism

{
    "status":"ok",
    "data": mnnmmlnlnmnlnklnklnklnklanksldf,
}

{
    "status":"error",
    "code":"400102",
    "message":"Server No NO",
}