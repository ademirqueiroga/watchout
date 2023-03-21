object Dependencies {

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeFoundation =
        "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeConstraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
    const val composeTv = "androidx.tv:tv-foundation:1.0.0-alpha03"
    const val composeTvMaterial = "androidx.tv:tv-material:1.0.0-alpha03"

    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    const val pagingCommonKtx = "androidx.paging:paging-common-ktx:${Versions.paging}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCommon = "androidx.room:room-common:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomTesting = "androidx.room:room-testing:${Versions.roomVersion}"
    const val roomPaging = "androidx.room:room-paging:${Versions.roomVersion}"

}