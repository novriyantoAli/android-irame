package co.id.digital.insinyur.irame.ui.payment

import co.id.digital.insinyur.irame.data.models.PaymentResponse

data class OperationResult(val mode: String, var data: PaymentResponse)