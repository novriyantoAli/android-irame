package co.id.digital.insinyur.irame.ui.invoice

import co.id.digital.insinyur.irame.data.models.InvoiceResponse

data class OperationResult(
    val mode: String,
    val result: InvoiceResponse?
)