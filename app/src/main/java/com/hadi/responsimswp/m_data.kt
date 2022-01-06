package com.hadi.responsimswp

class m_data {
    var id_mahasiswa: Int = 0
    var nim: String = ""
    var nama: String = ""
    var id_prodi: Int = 0
    var nama_prodi: String = ""


    constructor(id_mahasiswa: Int, nim: String, nama: String, id_prodi: Int, nama_prodi: String) {
        this.id_mahasiswa = id_mahasiswa
        this.nim = nim
        this.nama = nama
        this.id_prodi = id_prodi
        this.nama_prodi = nama_prodi
    }
}