import java.text.SimpleDateFormat
import java.util.*


//這是一個Long的extension function，所有的Long都可以使用，會把Long轉成時間的格式

fun Long.convertToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-d hh:mm a", Locale.getDefault())
    return format.format(date)
}


