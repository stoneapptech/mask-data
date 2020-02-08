@file:JvmName("Mask")
package mask.main

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.stream.JsonReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.lang.Exception
import java.net.URL
import java.nio.channels.Channels
import java.util.*


fun main(args: Array<String>) {
    if(args.isEmpty()) {
        println("You must provide a data path")
        return
    }
    val dataPath = args[0]
    val dataDir = File(dataPath)
    if(!dataDir.exists() || !dataDir.isDirectory) {
        println("The path you provide is either doesn't exist or isn't a directory")
        return
    }

    print("Downloading csv...")
    val filename = download()
    println("downloaded successfully, start parsing")
    val stores = parse(dataDir, filename)
    println("parsed successfully")

    print("Recording timestamp...")
    recordTime(dataDir, stores[0].datetime)
    println("Done")

    print("Generating area_available files... ")
    generateAreaAvailable(dataDir, stores)
    println("Done")

    print("Generating mask_number files... ")
    generateMaskNumber(dataDir, stores)
    println("Done")

}

fun download(): String {
    val url = URL("https://data.nhi.gov.tw/Datasets/Download.ashx?rid=A21030000I-D50001-001&l=https://data.nhi.gov.tw/resource/mask/maskdata.csv")
    val inputChannel = Channels.newChannel(url.openStream())
    val localFilename = "maskdata.csv"
    val fileChannel = FileOutputStream(localFilename).channel
    var pos = 0L
    while(fileChannel.transferFrom(inputChannel, pos, Long.MAX_VALUE) > 0) {
        pos += Long.MAX_VALUE
    }
    inputChannel.close()
    fileChannel.close()
    return localFilename
}

fun parse(dataDir: File, filename: String): Array<FullStore> {
    val csv = File(filename)
    val json = csvReader().readAllWithHeader(csv).joinToString(prefix = "[", postfix = "]") {
        var result = "{"
        it.forEach { (key, value) ->
            result += "\"$key\"=\"$value\","
        }
        result.dropLast(1) + "}"
    }
    val gson = GsonBuilder()
        .setDateFormat("yyyy/MM/dd HH:mm:ss")
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    val stores = gson.fromJson(json, Array<FullStore>::class.java)
    val addAreaCodeFailed = mutableListOf<Int>()
    stores.forEachIndexed { index, store ->
        val success = setupAreaCode(dataDir, store)
        if(!success) {
            addAreaCodeFailed.add(index)
        }
    }

    val failedStores = stores.filterIndexed { index, _ ->
        addAreaCodeFailed.contains(index)
    }
    notifyDevelopersForMissing(failedStores.toTypedArray())

    return stores.filter { it.areaCodeInited() }.toTypedArray()
}

fun setupAreaCode(dataDir: File, store: FullStore): Boolean {
    val storeInfoPath = dataDir.path + "/store/${store.code}.json"
    val storeInfo = File(storeInfoPath)
    if(!storeInfo.exists()) {
        createStoreFile(dataDir, store)
    }
    if(storeInfo.exists()) {
        val fileReader = FileReader(storeInfo)
        val jsonReader = JsonReader(fileReader)
        val json = Gson().fromJson<JsonObject>(jsonReader, JsonObject::class.java)
        val areaCode = json["area_id"].asString
        store.areaCode = areaCode
        jsonReader.close()
        fileReader.close()
        return true
    } else {
        return false
    }
}

fun createStoreFile(dataDir: File, store: FullStore) {
    try {
        PharmacyInjector.add(dataDir, store.code, store.name, store.address, store.tel)
    } catch (e: Exception) {
        //fail silently
    }
}

fun notifyDevelopersForMissing(stores: Array<FullStore>) {
//    if(stores.isEmpty()) {
//        return
//    }
//    println(stores.joinToString())
//
//    val receivers = JSONArray().put(
//                        JSONObject()
//                            .put("Email", "secminhrian@gmail.com")
//                            .put("Name", "Developers")
//                    ).put(
//                        JSONObject()
//                            .put("Email", "a91082900@gmail.com")
//                            .put("Name", "Developers")
//                    ).put(
//                        JSONObject()
//                            .put("Email", "t510599@gmail.com")
//                            .put("Name", "Developers")
//                    )
//    val text = "The following stores' addresses cannot be parsed, please add manually\n${stores.joinToString(separator = "\n")}"
//
//    val client = MailjetClient("", "")
//    val request = MailjetRequest(Emailv31.resource)
//        .property(Email.FROMEMAIL, "service@stoneapp.tech")
//        .property(Email.FROMNAME, "Stoneapp Service")
//        .property(Email.RECIPIENTS, receivers)
//        .property(Email.SUBJECT, "Store's address parsing error!")
//        .property(Email.TEXTPART, text)
//
//    val response = client.post(request)
//    println("Email sent")
//    println(response.status)
//    println(response.data)
}

fun recordTime(dataDir: File, time: Date) {
    val timePath = dataDir.path + "/update_time.txt"
    val timeFile = File(timePath)
    if(!timeFile.exists()) {
        timeFile.createNewFile()
    }
    timeFile.writeText(time.time.toString())
}

fun generateAreaAvailable(dataDir: File, stores: Array<FullStore>) {
    val areaAvailablePath = dataDir.path + "/area_available"
    val areaAvailableDir = File(areaAvailablePath)
    if(!areaAvailableDir.exists()) {
        areaAvailableDir.mkdir()
    }

    stores.groupBy { it.areaCode }.forEach { (areaCode, areaStores) ->
        val availableStores = areaStores.filter { it.adultMasksCount + it.childMasksCount > 0 }
        if(availableStores.isNotEmpty()) {
            val filePath = areaAvailablePath + "/$areaCode.json"
            val file = File(filePath)
            if(!file.exists()) {
                file.createNewFile()
            }

            file.writeText(availableStores.joinToString(prefix = "[", postfix = "]") { it.code })
        }
    }

}

fun generateMaskNumber(dataDir: File, stores: Array<FullStore>) {
    val maskNumberPath = dataDir.path + "/mask_number"
    val maskNumberDir = File(maskNumberPath)
    if(!maskNumberDir.exists()) {
        maskNumberDir.mkdir()
    }
    stores.forEach { store ->
        val filename = maskNumberPath + "/${store.code}.json"
        val file = File(filename)
        val content = "{\"adult\": ${store.adultMasksCount}, \"child\": ${store.childMasksCount}}\n"
        if(!file.exists()) {
            file.createNewFile()
        }
        file.writeText(content)
    }
}

fun<E> MutableList<E>.removeIndexes(indexes: IntArray) {
    for(index in indexes) {
        removeAt(index)
    }
}