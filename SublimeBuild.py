class Compile:
	def __init__(self, version, final):
		self.run(version, final)

	def run(self, version, final):
		import os	
		#os.system("cd Rocan && gradlew setupDecompWorkspace --stop && gradlew clean build")
		os.system("cd Rocan && gradlew runClient --stop && gradlew clean build")

		import shutil
		try:
			shutil.copyfile("Rocan/build/libs/rocan-" + version + "-all.jar", os.getenv("APPDATA") + "\\.minecraft\\mods\\rocan-" + final +".jar")
			os.system("start C:/Users/Public/Desktop/Minecraft_Launcher");
			print("Build has been finished. No errors.");
		except:
			print("A error ocurred in BUILD.");

		import sys
		sys.exit()

Compile("0.1", "0.1");