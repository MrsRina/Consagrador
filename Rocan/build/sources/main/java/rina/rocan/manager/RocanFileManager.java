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
import rina.rocan.client.RocanFriend;
import rina.rocan.client.RocanHUD;

// GUI.
import rina.rocan.gui.frame.RocanFrame;

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

	private String PATH_FILE_CLIENT  = PATH_MAIN + "client.json";
	private String PATH_FILE_MATRIX  = PATH_MAIN + "matrix.json";
	private String PATH_FILE_FRIENDS = PATH_MAIN + "friends.json";

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
			this.loadMatrix();
			this.loadFriends();
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
			this.saveMatrix();
			this.saveFriends();
		} catch (IOException exc) {
			// exc.printStackTrace();
		}
	}

	public void saveConfiguration() throws IOException {
		Gson GSON_BUILDER      = new GsonBuilder().setPrettyPrinting().create();
		JsonParser GSON_PARSER = new JsonParser();

		JsonObject MAIN_JSON      = new JsonObject();
		JsonObject MAIN_FRAMES_XY = new JsonObject();

		for (RocanFrame frames : Rocan.getClientGUI().getFrameList()) {
			JsonObject FRAME = new JsonObject();

			FRAME.add("tag", new JsonPrimitive(frames.getName()));
			FRAME.add("x", new JsonPrimitive(frames.getX()));
			FRAME.add("y", new JsonPrimitive(frames.getY()));

			MAIN_FRAMES_XY.add(frames.getName(), FRAME);
		}

		JsonObject FRAME_HUD_EDITOR = new JsonObject();

		FRAME_HUD_EDITOR.add("tag", new JsonPrimitive(Rocan.getClientGUI().getFrameHUD().getName()));
		FRAME_HUD_EDITOR.add("x", new JsonPrimitive(Rocan.getClientGUI().getFrameHUD().getX()));
		FRAME_HUD_EDITOR.add("y", new JsonPrimitive(Rocan.getClientGUI().getFrameHUD().getY()));

		MAIN_FRAMES_XY.add(Rocan.getClientGUI().getFrameHUD().getName(), FRAME_HUD_EDITOR);

		MAIN_JSON.add("client", new JsonPrimitive(Rocan.getClientName()));
		MAIN_JSON.add("version", new JsonPrimitive(Rocan.getClientVersion()));
		MAIN_JSON.add("prefix", new JsonPrimitive(Rocan.getCommandManager().getPrefix()));

		MAIN_JSON.add("frameList", MAIN_FRAMES_XY);

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

		if (MAIN_JSON.get("prefix") != null) {
			// Load prefix.
			Rocan.getCommandManager().setPrefix(MAIN_JSON.get("prefix").getAsString());
		}

		if (MAIN_JSON.get("frameList") != null) {
			JsonObject FRAME_LIST = MAIN_JSON.get("frameList").getAsJsonObject();

			for (RocanFrame frames : Rocan.getClientGUI().getFrameList()) {
				if (FRAME_LIST.get(frames.getName()) == null) {
					continue;
				}

				JsonObject FRAME = FRAME_LIST.get(frames.getName()).getAsJsonObject();

				RocanFrame frame_requested = Rocan.getClientGUI().getFrameByRectTag(FRAME.get("tag").getAsString());

				frame_requested.setX(FRAME.get("x").getAsInt());
				frame_requested.setY(FRAME.get("y").getAsInt());
			}

			JsonObject FRAME_HUD_EDITOR = FRAME_LIST.get(Rocan.getClientGUI().getFrameHUD().getName()).getAsJsonObject();
		
			Rocan.getClientGUI().getFrameHUD().setX(FRAME_HUD_EDITOR.get("x").getAsInt());
			Rocan.getClientGUI().getFrameHUD().setY(FRAME_HUD_EDITOR.get("y").getAsInt());
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

	public void saveMatrix() throws IOException {
		Gson GSON_BUILDER      = new GsonBuilder().setPrettyPrinting().create();
		JsonParser GSON_PARSER = new JsonParser();

		JsonObject MAIN_JSON = new JsonObject();

		// We create array list to HUDs dock.
		JsonArray MAIN_HUD_LEFT_UP    = new JsonArray();
		JsonArray MAIN_HUD_LEFT_DOWN  = new JsonArray();
		JsonArray MAIN_HUD_RIGHT_UP   = new JsonArray();
		JsonArray MAIN_HUD_RIGHT_DOWN = new JsonArray();

		for (RocanHUD huds : Rocan.getModuleManager().getHUDListLeftUp()) {
			MAIN_HUD_LEFT_UP.add(huds.getTag());
		}

		for (RocanHUD huds : Rocan.getModuleManager().getHUDListLeftDown()) {
			MAIN_HUD_LEFT_DOWN.add(huds.getTag());
		}

		for (RocanHUD huds : Rocan.getModuleManager().getHUDListRightUp()) {
			MAIN_HUD_RIGHT_UP.add(huds.getTag());
		}

		for (RocanHUD huds : Rocan.getModuleManager().getHUDListRightDown()) {
			MAIN_HUD_RIGHT_DOWN.add(huds.getTag());
		}

		MAIN_JSON.add("leftUp", MAIN_HUD_LEFT_UP);
		MAIN_JSON.add("leftDown", MAIN_HUD_LEFT_DOWN);
		MAIN_JSON.add("rightUp", MAIN_HUD_RIGHT_UP);
		MAIN_JSON.add("rightDown", MAIN_HUD_RIGHT_DOWN);

		JsonElement JSON_PRETTY_FORMAT = GSON_PARSER.parse(MAIN_JSON.toString());

		String STRING_JSON = GSON_BUILDER.toJson(JSON_PRETTY_FORMAT);

		cleanFile(PATH_FILE_MATRIX);
		verifyFile(PATH_FILE_MATRIX);

		OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_FILE_MATRIX), "UTF-8");

		file.write(STRING_JSON);
		file.close();
	}

	public void loadMatrix() throws IOException {
		InputStream JSON_FILE = Files.newInputStream(Paths.get(PATH_FILE_MATRIX));

		JsonObject MAIN_JSON = new JsonParser().parse(new InputStreamReader(JSON_FILE)).getAsJsonObject();

		Rocan.getModuleManager().clearHUDDockList();

		if (MAIN_JSON.get("leftUp") != null && MAIN_JSON.get("leftDown") != null && MAIN_JSON.get("rightUp") != null && MAIN_JSON.get("rightDown") != null) {
			JsonArray HUD_LEFT_UP    = MAIN_JSON.get("leftUp").getAsJsonArray();
			JsonArray HUD_LEFT_DOWN  = MAIN_JSON.get("leftDown").getAsJsonArray();
			JsonArray HUD_RIGHT_UP   = MAIN_JSON.get("rightUp").getAsJsonArray();
			JsonArray HUD_RIGHT_DOWN = MAIN_JSON.get("rightDown").getAsJsonArray();

			for (JsonElement elements : HUD_LEFT_UP) {
				String element = elements.getAsString();

				if (Rocan.getModuleManager().getModuleByTag(element) == null) {
					continue;
				}

				RocanHUD hud = (RocanHUD) Rocan.getModuleManager().getModuleByTag(element);

				Rocan.getModuleManager().addHUDListLeftUp(hud);
			}

			for (JsonElement elements : HUD_LEFT_DOWN) {
				String element = elements.getAsString();

				if (Rocan.getModuleManager().getModuleByTag(element) == null) {
					continue;
				}

				RocanHUD hud = (RocanHUD) Rocan.getModuleManager().getModuleByTag(element);

				Rocan.getModuleManager().addHUDListLeftDown(hud);
			}


			for (JsonElement elements : HUD_RIGHT_UP) {
				String element = elements.getAsString();

				if (Rocan.getModuleManager().getModuleByTag(element) == null) {
					continue;
				}

				RocanHUD hud = (RocanHUD) Rocan.getModuleManager().getModuleByTag(element);

				Rocan.getModuleManager().addHUDListRightUp(hud);
			}

			for (JsonElement elements : HUD_RIGHT_DOWN) {
				String element = elements.getAsString();

				if (Rocan.getModuleManager().getModuleByTag(element) == null) {
					continue;
				}

				RocanHUD hud = (RocanHUD) Rocan.getModuleManager().getModuleByTag(element);

				Rocan.getModuleManager().addHUDListRightDown(hud);
			}
		}

		JSON_FILE.close();
	}

	public void saveFriends() throws IOException {
		Gson GSON_BUILDER      = new GsonBuilder().setPrettyPrinting().create();
		JsonParser GSON_PARSER = new JsonParser();

		JsonObject MAIN_JSON = new JsonObject();

		// We create array list to HUDs dock.
		JsonArray MAIN_FRIENDS = new JsonArray();

		for (RocanFriend friends : Rocan.getFriendManager().getFriendList()) {
			MAIN_FRIENDS.add(friends.getName());
		}

		MAIN_JSON.add("friends", MAIN_FRIENDS);

		JsonElement JSON_PRETTY_FORMAT = GSON_PARSER.parse(MAIN_JSON.toString());

		String STRING_JSON = GSON_BUILDER.toJson(JSON_PRETTY_FORMAT);

		cleanFile(PATH_FILE_FRIENDS);
		verifyFile(PATH_FILE_FRIENDS);

		OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(PATH_FILE_FRIENDS), "UTF-8");

		file.write(STRING_JSON);
		file.close();
	}

	public void loadFriends() throws IOException {
		InputStream JSON_FILE = Files.newInputStream(Paths.get(PATH_FILE_FRIENDS));

		JsonObject MAIN_JSON = new JsonParser().parse(new InputStreamReader(JSON_FILE)).getAsJsonObject();

		Rocan.getModuleManager().clearHUDDockList();

		if (MAIN_JSON.get("friends") != null) {
			JsonArray FRIEND_LIST = MAIN_JSON.get("friends").getAsJsonArray();

			for (JsonElement elements : FRIEND_LIST) {
				String element = elements.getAsString();

				Rocan.getFriendManager().addFriend(element);
			}
		}

		JSON_FILE.close();
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