package mask.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class FullStore(
    @Expose @SerializedName("醫事機構代碼") val code: String,
    @Expose @SerializedName("醫事機構名稱") val name: String,
    @Expose @SerializedName("醫事機構地址") val address: String,
    @Expose @SerializedName("醫事機構電話") val tel: String,
    @Expose @SerializedName("成人口罩總剩餘數") val adultMasksCount: Int,
    @Expose @SerializedName("兒童口罩剩餘數") val childMasksCount: Int,
    @Expose @SerializedName("來源資料時間") val datetime: Date
) {
    lateinit var areaCode: String

    fun areaCodeInited(): Boolean {
        return ::areaCode.isInitialized
    }

    override fun toString(): String {
        return "$code $name $address $tel $adultMasksCount $childMasksCount $datetime"
    }
}