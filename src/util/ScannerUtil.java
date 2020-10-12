package util;

import java.util.Scanner;

public abstract class ScannerUtil {
	private static Scanner scan;
	
	public static Scanner getScanner() {
		if (scan == null) scan = new Scanner(System.in);
		return scan;
	}
	
	public static void close() {
		scan.close();
	}
}
