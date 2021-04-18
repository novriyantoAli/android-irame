package co.id.digital.insinyur.irame.data.repository

import co.id.digital.insinyur.irame.data.db.DB
import co.id.digital.insinyur.irame.data.models.FinanceReportResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.data.network.API
import co.id.digital.insinyur.irame.data.network.SafeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportRepository(private val db: DB, private val api: API): SafeAPI() {
    suspend fun financeCurrentDay(auth: String): List<FinanceReportResponse> {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.reportFinanceDay(bearer) }
        }
    }

    suspend fun expirationToday(auth: String): List<UsersResponse> {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.reportExpirationToday(bearer) }
        }
    }

    suspend fun financeCurrentMonth(auth: String): List<FinanceReportResponse> {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.reportFinanceMonth(bearer) }
        }
    }

    suspend fun financeCurrentYear(auth: String): List<FinanceReportResponse> {
        val bearer = "Bearer $auth"
        return withContext(Dispatchers.IO){
            apiRequest { api.reportFinanceYear(bearer) }
        }
    }
}