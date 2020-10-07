package th.co.cdg.foregroundserviceandroid.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import th.co.cdg.foregroundserviceandroid.data.Repository
import th.co.cdg.foregroundserviceandroid.presentation.MainViewModel
import th.co.cdg.foregroundserviceandroid.presentation.foreground.AwesomeForegroundService


val presentationModules = module {
    //viewModel
    viewModel { MainViewModel(get()) }
}