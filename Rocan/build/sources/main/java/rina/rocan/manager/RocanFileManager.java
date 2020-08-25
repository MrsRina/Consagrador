package rina.rocan.manager;

// Gson.
import com.google.gson.*;

// Java.
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import java.awt.*;
import java.io.*;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Rocan.
import rina.rocan.Rocan;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanFileManager {
	private String PATH_MAIN    = "rocan/";
	private String PATH_MODULES = PATH_MAIN + "modules/";

	private String PATH_FILE_CLIENT = PATH_MAIN + "client.json";

	public RocanFileManager() {
		try {
			verifyFolder(PATH_MAIN);
			verifyFolder(PATH_MODULES);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	public void loadClient() {
		try {
			this.loadConfiguration();
			this.loadModules();
		} catch (IOException exc) {
			// exc.printStackTrace();
		}
	}

	public void saveClient() {
		try {
			verifyFolder(PATH_MAIN);
			verifyFolder(PATH_MODULES);

			this.saveConfiguration();
			this.saveModules();
		} catch (IOException exc) {
			// exc.printStackTrace();
		}
	}

	public void saveConfiguration() throws IOException {
		Gson GSON_BUILDER      = new GsonBuilder().setPrettyPrinting().create();
		JsonParser GSON_PARSER = new JsonParser();

		JsonObject MAIN_JSON                 = new JsonObject();
		JsonObject MAIN_CLIENT_CONFIGURATION = new JsonObject();

		MAIN_JSON.add("Client", new JsonPrimitive(Rocan.getClientName()));
		MAIN_JSON.add("Version", new JsonPrimitive(Rocan.getClientVersion()));
		MAIN_JSON.add("Prefix", new JsonPrimitive(Rocan.getCommandManager().getPrefix()));

		JsonElement JSON_PRETTY_FORMAT = GSON_PARSER.parse(MAIN_JSON.toString());

		String STRING_JSON = GSON_BUILDER.toJson(JSON_PRETTY_FORMAT);

		cleanFile(PATH_FILE_CLIENT);
		verifyFile(PATH_FILE_CLIENT);

		OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_FILE_CLIENT), "UTF-8");

		file.write(STRING_JSON);
		file.close();
	}

	public void loadConfiguration() throws IOException {
		InputStream JSON_FILE = Files.newInputStream(Paths.get(PATH_FILE_CLIENT));

		JsonObject MAIN_JSON = new JsonParser().parse(new InputStreamReader(JSON_FILE)).getAsJsonObject();

		if (MAIN_JSON.get("Prefix") != null) {
			// Load prefix.
			Rocan.getCommandManager().setPrefix(MAIN_JSON.get("Prefix").getAsString());
		}

		JSON_FILE.close();
	}

	public void saveModules() throws IOException {
		Gson GSON_BUILDER      = new GsonBuilder().setPrettyPrinting().create();
		JsonParser GSON_PARSER = new JsonParser();

		for (RocanModule modules : Rocan.getModuleManager().getModuleList()) {
			String path = getPathModules() + modules.getCategory().getTag().toLowerCase() + "/";

			JsonObject MAIN_JSON = new JsonObject();
	
			MAIN_JSON.add("name", new JsonPrimitive(modules.getName()));
			MAIN_JSON.add("tag", new JsonPrimitive(modules.getTag()));

			MAIN_JSON.add("settingList", modules.getJsonObjectSettingList());

			JsonElement JSON_PRETTY_FORMAT = GSON_PARSER.parse(MAIN_JSON.toString());

			String STRING_JSON = GSON_BUILDER.toJson(JSON_PRETTY_FORMAT);

			verifyFolder(path);
			verifyFile(path + modules.getTag() + ".json");

			OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(path + modules.getTag() + ".json"), "UTF-8");

			file.write(STRING_JSON);
			file.close();
		}
	}

	public void loadModules() throws IOException {
		for (RocanModule modules : Rocan.getModuleManager().getModuleList()) {
			String path = getPathModules() + modules.getCategory().getTag().toLowerCase() + "/";

			InputStream JSON_FILE = Files.newInputStream(Paths.get(path + modules.getTag() + ".json"));

			JsonObject MAIN_JSON = new JsonParser().parse(new InputStreamReader(JSON_FILE)).getAsJsonObject();

			if (MAIN_JSON.get("name") == null || MAIN_JSON.get("tag") == null || MAIN_JSON.get("settingList") == null) {
				continue;
			}

			RocanModule module_requested = Rocan.getModuleManager().getModuleByTag(MAIN_JSON.get("tag").getAsString());

			module_requested.loadSettingListFromJsonObject(MAIN_JSON.get("settingList").getAsJsonObject());

			JSON_FILE.close();
		}
	}

	public void reloadModules() {
		for (RocanModule modules : Rocan.getModuleManager().getModuleList()) {
			modules.reloadModule();
		}
	}

	// Utils.
	public void verifyFolder(String path) throws IOException {
		if (!Files.exists(Paths.get(path))) {
			Files.createDirectories(Paths.get(path));
		}
	}

	public void verifyFile(String path) throws IOException {
		if (!Files.exists(Paths.get(path))) {
			Files.createFile(Paths.get(path));
		}
	}

	public void cleanFile(String path) throws IOException {
		File file = new File(path);

		file.delete();
	}

	public String getPathMain() {
		return this.PATH_MAIN;
	}

	public String getPathModules() {
		return this.PATH_MODULES;
	}

	public String getPathFileClient() {
		return this.PATH_FILE_CLIENT;
	}
}