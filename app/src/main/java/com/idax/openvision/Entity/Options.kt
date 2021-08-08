package com.idax.openvision.Entity

import android.os.Parcelable
import com.idax.openvision.R
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


//@Parcelize
data class Option(val id: Int, val title: String, var fragmentId: Int) : Serializable

//@Parcelize
class Options : Serializable {
    var optionList: List<Option>

    init {
        optionList = listOf(
            //Option(0, "Option 1", R.id.optionOneFragment),//action_mainOptions_to_optionOne
            Option(0, "Option 1", R.id.action_mainOptions_to_optionOne),
            Option(1, "Option 2", R.id.action_mainOptions_to_optionTwo),
            Option(2, "Custom Camera", R.id.action_mainOptions_to_camera),
            Option(3, "OpenCV", R.id.action_mainOptions_to_opencv),
            Option(4, "Room DB", R.id.action_mainOptions_to_roomdb),
            Option(5, "API Rest", R.id.action_mainOptions_to_apirest),
            Option(6, "Dynamic", R.id.action_mainOptions_to_dynamic)
        )
    }

    fun getData(): List<Option> {
        return optionList
    }
}
