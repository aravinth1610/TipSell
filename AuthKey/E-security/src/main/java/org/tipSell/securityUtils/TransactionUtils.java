package org.tipSell.securityUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

public class TransactionUtils {

	public static String readConfigFile(String path) {
		StringBuilder result = new StringBuilder();
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			if (inputStream == null) {

				throw new IllegalArgumentException("File not found: " + path);
			}

			String line;
			while ((line = br.readLine()) != null) {

				result.append(line);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Configuration file not found");
		} catch (IOException e) {
			throw new RuntimeException("Error reading configuration file");
		}
		return result.toString();
	}

	public static JSONObject replacePlaceholders(String baseConfigString, String realms, String cisiKey,String grantType, String basePath) {
		String uri = baseConfigString.replace("{realm}", realms).replace("{cisiKey}", cisiKey).replace("{basePath}",basePath).replace("{grantType}",grantType);

		// Convert the string into a JSON object
		JSONObject baseConfigObject = new JSONObject(uri);
		
		// Extract the 'COMMON' section
		JSONObject commonConfig = baseConfigObject.getJSONObject("COMMON");
				
		 // Extract the grant type-specific configuration (this should be a JSONObject, not a JSONArray)
        JSONObject grantTypeConfig = baseConfigObject.getJSONObject(grantType);

		// Insert 'COMMON' settings into the specific grant type
		grantTypeConfig.put(grantType.toLowerCase(), commonConfig);
		
		// Create the updated configuration
		JSONObject updatedConfig = new JSONObject();
		updatedConfig.put("URI", grantTypeConfig);
		
		return updatedConfig;
	}

}
