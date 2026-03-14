plugins {
	id("fabric-loom") version "1.15-SNAPSHOT"
	id("maven-publish")
	id("me.modmuss50.mod-publish-plugin") version "1.0.0"
}

val javaVersion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
	JavaVersion.VERSION_21 else JavaVersion.VERSION_17
java.targetCompatibility = javaVersion
java.sourceCompatibility = javaVersion

base.archivesName = "${property("mod_id")}"
version = "${property("mod_version")}+${stonecutter.current.project}+${property("mod_subversion")}"

repositories {

}

dependencies {
	minecraft("com.mojang:minecraft:${stonecutter.current.version}")
	mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:0.16.14")

	// Fabric API
	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
}

tasks {
	processResources {
		inputs.property("version", project.version)
		inputs.property("min_supported", project.property("min_supported_version"))
		inputs.property("max_supported", project.property("max_supported_version"))

		filesMatching("fabric.mod.json") {
			expand(
				mutableMapOf(
					"version" to project.version,
					"min_supported" to project.property("min_supported_version"),
					"max_supported" to project.property("max_supported_version")
				)
			)
		}

		val mixin = if (stonecutter.eval(stonecutter.current.version, ">=1.21"))
			"JukeboxManagerMixin" else "JukeboxBlockEntityMixin"

		filesMatching("jukebox_looping.mixins.json") {
			expand(
				mutableMapOf(
					"mixin" to mixin
				)
			)
		}
	}

	withType<JavaCompile> {
		val java = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
		options.release.set(java)
	}

	java {
		withSourcesJar()
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesName.get()}"}
		}
	}
}

publishMods {
	file = tasks.remapJar.get().archiveFile
	additionalFiles.from(tasks.remapSourcesJar.get().archiveFile)
	displayName = "Jukebox Looping ${project.version}"
	version = "${project.version}"
	changelog = rootProject.file("CHANGELOG.md").readText()
	type = STABLE
	modLoaders.addAll("fabric", "quilt")

	val modrinthToken = providers.environmentVariable("MODRINTH_TOKEN")
	val discordToken = providers.environmentVariable("DISCORD_TOKEN")

	dryRun = modrinthToken.getOrNull() == null || discordToken.getOrNull() == null

	modrinth {
		accessToken = modrinthToken
		projectId = "8G8TjZrI"

		minecraftVersionRange {
			start = "${property("min_supported_version")}"
			end = "${property("max_supported_version")}"
		}

		requires {
			// Fabric API
			id = "P7dR8mSH"
		}
	}

	if (stonecutter.current.project == "1.21") {
		discord {
			webhookUrl = discordToken

			username = "Jukebox Looping Updates"

			avatarUrl = "https://github.com/PneumonoIsNotAvailable/JukeboxLooping/blob/master/src/main/resources/assets/jukebox_looping/icon.png?raw=true"

			content = changelog.map { "# Jukebox Looping version ${project.property("mod_version")}\n<@&1472490332783378472>\n" + it }
		}
	}
}

// configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}