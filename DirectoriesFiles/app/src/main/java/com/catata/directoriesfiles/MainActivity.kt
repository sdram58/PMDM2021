package com.catata.directoriesfiles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Parcel
import android.os.Parcelable
import com.catata.directoriesfiles.databinding.ActivityMainBinding
import java.io.*
import java.lang.Exception

const val INTERNAL_FILE = "InternalFile.txt"
const val EXTERNAL_FILE = "External_File.txt"
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        binding.btnLoadExt.setOnClickListener {
            loadFromExternal()
        }
        binding.btnLoadInt.setOnClickListener {
            loadFromInternal()
        }
        binding.btnSaveInt.setOnClickListener {
            saveToInternal()
        }
        binding.btnSaveExt.setOnClickListener {
            saveToExternal()
        }

    }

    private fun saveToInternal() {
        val p = People(
            binding.etName.text.toString(),
            binding.etSurname.text.toString()
        )
        openFileOutput(INTERNAL_FILE, Context.MODE_PRIVATE).use {
            val oos = ObjectOutputStream(it)
            oos.writeObject(p)
            oos.close()
        }
    }

    private fun loadFromInternal() {
        openFileInput(INTERNAL_FILE).use {
            val ois = ObjectInputStream(it)

            val p:People = ois?.readObject() as People

            binding.etName.setText(p.name)
            binding.etSurname.setText(p.surname)
        }
    }

    private fun saveToExternal() {
        if (isExternalStorageWritable()) {
            val p = People(
                binding.etName.text.toString(),
                binding.etSurname.text.toString()
            )
            val fileExt = File(getExternalFilesDir(null), EXTERNAL_FILE)
            val fos = FileOutputStream(fileExt)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(p)
            oos.close()
            fos.close()
        }
    }

    private fun loadFromExternal() {
        if(isExternalStorageReadable()){
            val fileExt = File(getExternalFilesDir(null), EXTERNAL_FILE)
            val fis = FileInputStream(fileExt)
            val ois = ObjectInputStream(fis)
            val p:People = ois.readObject() as People
            ois.close()
            fis.close()
            binding.etName.setText(p.name)
            binding.etSurname.setText(p.surname)
        }
    }

    // Checks if a volume containing external storage is available
// for read and write.
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }


    data class People(
        val name:String?,
        val surname:String?
            ):Serializable

}