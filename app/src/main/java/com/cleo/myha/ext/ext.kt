
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

// 這是一個Long的extension function，所有的Long都可以使用，會把Long轉成時間的格式

fun Long.convertToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-d hh:mm a", Locale.getDefault())
    return format.format(date)
}

fun Long.convertDurationToDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM d yyyy", Locale.getDefault())
    return format.format(date)
}

fun Long.convertDurationToCertain(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MM,d,yyyy", Locale.getDefault())
    return format.format(date)
}

fun Fragment.hideKeyboard(view: View) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Long.toFormat(millis: Long): String {
    val hours = millis / (1000 * 60 * 60)
    val minutes = millis / (1000 * 60) - (hours * 60)
    val newHour = if (hours == -1L) {
        "00"
    } else hours

    val newMinutes = if (minutes == -1L || minutes == 0L) {
        "00"
    } else minutes
    return "$newHour:$newMinutes"
}