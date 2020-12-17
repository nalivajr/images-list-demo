package by.nalivajr.imagelistdemo.domain.model

import android.os.Parcel
import android.os.Parcelable

data class ImagesPage(
    val images: List<ImageInfo> = emptyList()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ImageInfo.CREATOR)?.toList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(images)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ImagesPage> {
            override fun createFromParcel(parcel: Parcel): ImagesPage {
                return ImagesPage(parcel)
            }

            override fun newArray(size: Int): Array<ImagesPage?> {
                return arrayOfNulls(size)
            }
        }
    }

}