package Utility.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import Utility.ExtentReports.ExtentRepotEx;

//Ajit Nakum - This class is for make report as ZIP and send it to the recepients.
public class FolderZipClass extends ConfigClass {
	public String reportPath = properties.getProperty("reportPath") + "\\Report_" + ExtentRepotEx.timestamp;

	public void ZipFolder_method() throws Exception {
		zipFolder(reportPath, reportPath + "." + "zip");
	}

	public void zipFolder(String srcFolder, String destZipFile) throws Exception {
		FolderZipClass fz = new FolderZipClass();
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);

		fz.addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
	}

	public void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
		FolderZipClass fz1 = new FolderZipClass();
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			fz1.addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}

	public void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
}
