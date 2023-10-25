package com.dimasalamsyah.soaltest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseApi(

	@field:SerializedName("data")
	val data: List<BarangModel?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ResponseTransaksiHeaderApi(

	@field:SerializedName("data")
	val data: List<TransaksiHeaderModel?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ResponseTransaksiDetailApi(

	@field:SerializedName("data")
	val data: List<TransaksiDetailModel?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class BarangModel (

	@field:SerializedName("jumlahbarang ")
	var jumlahbarang : Int? = null,

	@field:SerializedName("stokbarang")
	val stokbarang: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("kodebarang")
	val kodebarang: String? = null,

	@field:SerializedName("namabarang")
	val namabarang: String? = null
): Serializable


data class TransaksiHeaderModel (

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jenistransaksi")
	val jenistransaksi: String? = null,

	@field:SerializedName("nomortransaksi")
	val nomortransaksi: String? = null
): Serializable

data class TransaksiDetailModel(

	@field:SerializedName("barang")
	val barang: BarangModel? = null,

	@field:SerializedName("jumlahbarang")
	val jumlahbarang: Int? = null,

	@field:SerializedName("idbarang")
	val idbarang: Int? = null,

	@field:SerializedName("idtransaksi")
	val idtransaksi: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
