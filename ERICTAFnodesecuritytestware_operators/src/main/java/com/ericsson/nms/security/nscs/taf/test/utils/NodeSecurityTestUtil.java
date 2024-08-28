/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.nms.security.nscs.taf.test.utils;

import static com.ericsson.nms.security.nscs.taf.test.getters.NscsServiceGetter.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;

public class NodeSecurityTestUtil {

    private static final Logger LOGGER = Logger
            .getLogger(NodeSecurityTestUtil.class);


    /**
     * Convert stream to file
     *
     * @param inputStream input stream of file
     * @param fileName name of the file
     * @return file
     */
    public static File inputStreamToFile(InputStream inputStream,
                                         String fileName) {
        Reader reader;
        Writer writer;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            reader = fileName.endsWith("xml") ? new InputStreamReader(
                    inputStream) : new InputStreamReader(inputStream, "UTF-8");

            writer = fileName.endsWith("xml") ? new OutputStreamWriter(
                    new FileOutputStream(fileName)) : new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF-8");

            bufferedWriter = new BufferedWriter(writer);
            bufferedReader = new BufferedReader(reader);
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new File(fileName);
    }

    /**
     * Get stream from file on a relative path
     *
     * @param absoluteFilePath - relative file path
     * @return stream
     */
    public static InputStream getStreamFromAbsoluteFilePath(
            String absoluteFilePath) {
        try {
            InputStream inputStream = NodeSecurityTestUtil.class.getResource(
                    absoluteFilePath).openStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * display content of a text file
     *
     * @param file - file handler to read file
     */
	public static void displayTextFile(File file) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("File Name : " + file.getName());
				LOGGER.debug("File content : "
						+ FileUtils.readFileToString(file));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * create byte array for string
     *
     * @param data string data for converting to bytes
     */
    public static byte[] createBytesFromString(String data) {
        byte[] byteData = null;
        try {
            byteData = data.trim().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteData;
    }

    /**
     * create file from byte array
     *
     * @param data byte data for writing to file
     * @return File
     */
    public static File createFileFromByte(byte[] data, String fileName) {
        fileName = StringUtils.isBlank(fileName) ? FILE_ABC_TXT : fileName;
        final File file = new File(fileName);
        try {
            FileUtils.writeByteArrayToFile(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * load template file to a hashmap
     *
     * @param fileName file name for loading
     * @param relPath relative path of the file
     * @return Map
     */
    public static Map<String, Object> templateLoader(String fileName, String relPath) {
        Map<String, Object> properties = new HashMap<>();
        relPath = StringUtils.isBlank(relPath) ? IPSEC_DATA_FILE_PATH : relPath;
        properties.put("fileName", fileName);
        properties.put("filePath", relPath + fileName);
        return Collections.unmodifiableMap(properties);
    }

    /**
     * split string to token using reg exp
     *
     * @param string string for matching
     * @param regExp regular expression for pattern matching
     * @return list of token
     */
    @SuppressWarnings("unchecked")
    public static List<String> splitStringToToken(String string, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(string);
        List<String> token = new ArrayList<>();
        while (matcher.find()) {
            token.add(matcher.group(1));
        }
        return token.size() == 0 ? Collections.EMPTY_LIST : token;
    }
    
	/**
	 * return the file name from the command string
	 * 
	 * @param String command
	 * @return file name in the command
	 */
	public static String getFileName(String command) {
		return command.substring(command.lastIndexOf(":") + 1);
	}
	
    
/*  public static Host getNMServer() {
        final Host apacheHost = HostConfigurator.getApache();
        return apacheHost;
    } */
    
    //TODO Waiting hostConfigurator align with Cluster id 239 for KVM
    public static Host getSecServiceUnit1() {
		Host result = DataHandler.getHostByName("internal_secserv_1");
		// CaP: Added print (N.I.)
		if (result != null) {
			LOGGER.info("HOST NAME = internal_secserv_1");
		}
		if (result == null) {
			result = DataHandler.getHostByName("secserv_1");
			LOGGER.info("HOST NAME = secserv_1");
		}
		// CaP: Added if!
		if (result == null) {
			result = DataHandler.getHostByName("secserv_1-internal");
			LOGGER.info("HOST NAME = secserv_1-internal");
		}
		return result;
	}

}
