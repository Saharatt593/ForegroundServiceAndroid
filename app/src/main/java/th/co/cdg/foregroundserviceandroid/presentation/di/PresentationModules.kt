package th.co.cdg.foregroundserviceandroid.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import th.co.cdg.foregroundserviceandroid.presentation.MainViewModel


val presentationModules = module {
    //viewModel
    viewModel { MainViewModel(get()) }

}