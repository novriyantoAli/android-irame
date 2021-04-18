package co.id.digital.insinyur.irame

import android.app.Application
import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.Interceptor
import co.id.digital.insinyur.irame.data.repository.*
import co.id.digital.insinyur.irame.ui.authenticate.AuthenticateViewModelFactory
import co.id.digital.insinyur.irame.ui.configuration.ConfigurationViewModel
import co.id.digital.insinyur.irame.ui.configuration.ConfigurationViewModelFactory
import co.id.digital.insinyur.irame.ui.invoice.InvoiceViewModelFactory
import co.id.digital.insinyur.irame.ui.main.MainViewModelFactory
import co.id.digital.insinyur.irame.ui.menu.MenuViewModelFactory
import co.id.digital.insinyur.irame.ui.nas.NasViewModelFactory
import co.id.digital.insinyur.irame.ui.packages.PackagesViewModelFactory
import co.id.digital.insinyur.irame.ui.payment.PaymentViewModelFactory
import co.id.digital.insinyur.irame.ui.profile.ProfileViewModelFactory
import co.id.digital.insinyur.irame.ui.reseller.ResellerViewModelFactory
import co.id.digital.insinyur.irame.ui.trace.TraceViewModel
import co.id.digital.insinyur.irame.ui.trace.TraceViewModelFactory
import co.id.digital.insinyur.irame.ui.transaction.TransactionViewModelFactory
import co.id.digital.insinyur.irame.ui.users.UsersViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Application: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@Application))

        // singleton
        bind() from singleton { Interceptor(instance()) }
        bind() from singleton { DB(instance()) }
        bind() from singleton { API(instance(), instance()) }

        // singleton
        bind() from singleton { AuthenticateRepository(instance(), instance()) }
        bind() from singleton { MenuRepository(instance(), instance()) }
        bind() from singleton { PackagesRepository(instance(), instance()) }
        bind() from singleton { RadiusRepository(instance(), instance()) }
        bind() from singleton { ResellerRepository(instance(), instance()) }
        bind() from singleton { TransactionRepository(instance(), instance()) }
        bind() from singleton { ApplicationRepository(instance()) }
        bind() from singleton { InvoiceRepository(instance(), instance()) }
        bind() from singleton { PaymentRepository(instance(), instance()) }
        bind() from singleton { ReportRepository(instance(), instance()) }
        // provider
        bind() from provider { AuthenticateViewModelFactory(instance()) }
        bind() from provider { MainViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { MenuViewModelFactory(instance(),instance(), instance(), instance()) }
        bind() from provider { ResellerViewModelFactory(instance(), instance()) }
        bind() from provider { TransactionViewModelFactory(instance(), instance()) }
        bind() from provider { PackagesViewModelFactory(instance(), instance()) }
        bind() from provider { ProfileViewModelFactory(instance(), instance()) }
        bind() from provider { UsersViewModelFactory(instance(), instance(), instance(), instance()) }
        bind() from provider { TraceViewModelFactory(instance(), instance()) }
        bind() from provider { NasViewModelFactory(instance(), instance()) }
        bind() from provider { ConfigurationViewModelFactory(instance()) }
        bind() from provider { InvoiceViewModelFactory(instance(), instance()) }
        bind() from provider { PaymentViewModelFactory(instance(), instance()) }
    }
}