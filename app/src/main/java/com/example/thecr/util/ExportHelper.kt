package com.example.thecr.util

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import com.example.thecr.model.StudentAttendanceScoreList
import java.io.File
import java.io.FileOutputStream

object ExportHelper {
    fun exportToCSV(studentAttendanceScoreList: StudentAttendanceScoreList,context: Context): File {
        val HEADER = "Name, Score"

        val filename = "${studentAttendanceScoreList.year_branch}.csv"

        val path = context.getExternalFilesDir(null)
        val fileOut = File(path, filename)

        fileOut.delete()

        fileOut.createNewFile()

        fileOut.appendText(HEADER)
        fileOut.appendText("\n")
        studentAttendanceScoreList.studentList.toSortedMap().forEach {
            fileOut.appendText("${it.key},${it.value}")
            fileOut.appendText("\n")
        }

        return fileOut
    }

    fun exportToPDF(studentAttendanceScoreList: StudentAttendanceScoreList): File {
        val document = PdfDocument()

        val HEADER = "Name, Score"

        val filename = "${studentAttendanceScoreList.year_branch}.pdf"

        val path = Environment.getExternalStorageDirectory()
        val fileOut = File(path, filename)

        fileOut.delete()


        fileOut.createNewFile()

        fileOut.appendText(HEADER)
        fileOut.appendText("\n")
        studentAttendanceScoreList.studentList.toSortedMap().forEach {
            fileOut.appendText("${it.key},${it.value}")
            fileOut.appendText("\n")
        }

        document.writeTo(FileOutputStream(fileOut))
        document.close()
        return fileOut
    }
}