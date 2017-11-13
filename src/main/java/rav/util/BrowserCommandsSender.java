package rav.util;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowserCommandsSender {
    public static void openUrl(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            openSystemSpecific(url);
        }
    }

    private static boolean openSystemSpecific(String url) {
        String preparedUrl = addProtocol(url);
        switch (getOs()) {
            case LINUX:
            case SOLARIS: {
                if (runCommand("kde-open " + preparedUrl)) return true;
                if (runCommand("gnome-open " + preparedUrl)) return true;
                if (runCommand("xdg-open " + preparedUrl)) return true;
            }
            case MAC_OS: return runCommand("open " + preparedUrl);
            case WINDOWS: return runCommand("explorer " + preparedUrl);
        }
        return false;
    }

    private static String addProtocol(String url) {
        if (!url.contains("://")) {
            return "http://" + url;
        }
        String[] parts = url.split("://");
        if (parts[0].equals("https")) {
            return url;
        } else {
            return "http://" + parts[1];
        }
    }

    private static boolean runCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            if (p == null) return false;

            try {
                int retval = p.exitValue();
                if (retval == 0) {
                    // Process ended immediately
                    return false;
                } else {
                    // Process crashed
                    return false;
                }
            } catch (IllegalThreadStateException itse) {
                // Process is running
                return true;
            }
        } catch (IOException e) {
            // Error running command
            return false;
        }
    }


    private enum EnumOS {
        LINUX, MAC_OS, SOLARIS, WINDOWS, UNKNOWN;
    }

    private static EnumOS getOs() {

        String s = System.getProperty("os.name").toLowerCase();

        if (s.contains("win")) {
            return EnumOS.WINDOWS;
        }

        if (s.contains("mac")) {
            return EnumOS.MAC_OS;
        }

        if (s.contains("solaris")) {
            return EnumOS.SOLARIS;
        }

        if (s.contains("sunos")) {
            return EnumOS.SOLARIS;
        }

        if (s.contains("linux")) {
            return EnumOS.LINUX;
        }

        if (s.contains("unix")) {
            return EnumOS.LINUX;
        } else {
            return EnumOS.UNKNOWN;
        }
    }
}
