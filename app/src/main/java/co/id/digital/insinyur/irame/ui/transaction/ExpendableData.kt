package co.id.digital.insinyur.irame.ui.transaction

import co.id.digital.insinyur.irame.data.models.TransactionResponse

data class ExpendableData(val title: List<String>, val data: HashMap<String, List<TransactionResponse>>)