import java.io.{File, PrintWriter}
import java.nio.file.Files

trait FileUtil {

  def createTmpFile(prefix: String, fileContent: List[String]): File = {
    val path = Files.createTempFile(prefix, null)
    val pw = new PrintWriter(path.toFile)
    fileContent.foreach(pw.println)
    pw.close()
    path.toFile
  }
}
