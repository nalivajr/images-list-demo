package by.nalivajr.imagelistdemo.domain.model

import android.os.Parcel
import android.os.Parcelable

data class ImageInfo(
    val id: Int,
    val url: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(url)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImageInfo> = object: Parcelable.Creator<ImageInfo> {
            override fun createFromParcel(parcel: Parcel): ImageInfo {
                return ImageInfo(parcel)
            }

            override fun newArray(size: Int): Array<ImageInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}