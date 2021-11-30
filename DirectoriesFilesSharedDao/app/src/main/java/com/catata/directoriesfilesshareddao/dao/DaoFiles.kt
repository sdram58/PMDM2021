package com.catata.directoriesfilesshareddao.dao

import android.content.Context
import android.os.Environment
import com.catata.directoriesfilesshareddao.model.People
import java.io.*

const val EXTERNAL_FILE = "External_File.txt"
class DaoFiles(val context: Context): IMyDAO {

    companion object{
        var daofile:DaoFiles? = null

        fun getInstance(c:Context):DaoFiles {

            daofile?.let {} ?: run {
                daofile = DaoFiles(c)
            }

            return daofile!!
        }

    }
    override suspend fun save(people: People, onSaved: OnSaved, onError: OnError?) {
        if (isExternalStorageWritable()) {

            val fileExt = File(context.getExternalFilesDir(null), EXTERNAL_FILE)
            val fos = FileOutputStream(fileExt)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(people)
            oos.close()
            fos.close()
            onSaved(true)
        }else{
            onError?.let {
                onError("Data can't be saved")
            }
        }
    }

    override suspend fun load(onLoaded: OnLoaded, onError: OnError?) {
        if(isExternalStorageReadable()){
            val fileExt = File(context.getExternalFilesDir(null), EXTERNAL_FILE)
            if(fileExt.exists()){
                val fis = FileInputStream(fileExt)
                val ois = ObjectInputStream(fis)
                val p: People = ois.readObject() as People
                ois.close()
                fis.close()
                onLoaded(p)
            }


        }else{
            onError?.let{
                onError("Data can't be loaded")
            }
        }
    }

    // Checks if a volume containing external storage is available
    // for read and write.
    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
}