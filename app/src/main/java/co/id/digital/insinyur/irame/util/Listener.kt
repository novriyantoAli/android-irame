package co.id.digital.insinyur.irame.util

import co.id.digital.insinyur.irame.data.models.*

interface MenuItemListener {
    fun onItemClick(item: MenuResponse)
}

interface MenuOptionListener {
    fun onOptionItemClick(id: Int)
}

interface ResellerOptionListener {
    fun onOptionItemClick(id: Int, resellerMenu: Int, index: Int)
}

interface PackageOptionListener {
    fun onPackageItemClick(menu: Int, packageResponse: PackageResponse)
}

interface ProfileOptionListener {
    fun onOptionItemClick(id: Int, profile: RadusergroupResponse)
}

interface UsersOptionListener {
    fun onOptionItemClick(id: Int, users: UsersResponse)
}

interface TraceOptionListener {
    fun onOptionItemClick(id: Int, rpa: RadpostauthResponse)
}

interface InvoiceOptionListener {
    fun onOptionItemClick(id: Int, invoice: InvoiceResponse)
}

interface PaymentOptionListener {
    fun onOptionItemClick(id: Int, payment: PaymentResponse)
}
