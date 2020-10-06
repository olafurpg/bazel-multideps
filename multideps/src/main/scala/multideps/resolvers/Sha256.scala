package multideps.resolvers

import java.io.BufferedInputStream
import java.io.File
import java.nio.file.Files
import java.security.MessageDigest

object Sha256 {
  def compute(file: File): String = {
    val sha = MessageDigest.getInstance("SHA-256")
    val fos = Files.newInputStream(file.toPath())
    try {
      val in = new BufferedInputStream(fos)
      val data = Array.ofDim[Byte](16384)
      var n = fos.read(data, 0, data.length)
      while (n != -1) {
        if (n > 0) sha.update(data, 0, n)
        n = in.read(data, 0, data.length)
      }
    } finally {
      fos.close()
    }
    bytesToHex(sha.digest())
  }
  private val hexArray = "0123456789abcdef".toCharArray
  private def bytesToHex(bytes: Array[Byte]): String = {
    val hexChars = new Array[Char](bytes.length * 2)
    var j = 0
    while (j < bytes.length) {
      val v: Int = bytes(j) & 0xff
      hexChars(j * 2) = hexArray(v >>> 4)
      hexChars(j * 2 + 1) = hexArray(v & 0x0f)
      j += 1
    }
    new String(hexChars)
  }
}
