package org.ourgrid.virt.strategies;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.ourgrid.virt.model.ExecutionResult;

public class LinuxUtils {

	public static final String LINUX_PERMISSIONS_FILE = "/etc/sudoers";
	
	public static void appendLineToSudoersFile(String userName, String line)
			throws IOException, FileNotFoundException {
		String sudoers = IOUtils.toString(new FileReader(LINUX_PERMISSIONS_FILE));
		String permissionLine = "\n" + userName + " ALL=NOPASSWD: " + line;
		
		if ( !sudoers.contains(permissionLine) ){
			
			ProcessBuilder sudoersAppendProc = new ProcessBuilder("/bin/echo",permissionLine, ">>", LINUX_PERMISSIONS_FILE);
			try {
				HypervisorUtils.runAndCheckProcess(sudoersAppendProc);
			} catch (Exception e) {
				System.out.println("Could not prepare environment for user " + userName + "\n" +
						e.getMessage());
			}
		}
	}
	
}
