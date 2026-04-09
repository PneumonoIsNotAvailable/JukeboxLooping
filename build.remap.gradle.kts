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
	mappings(loom.officialMojangMappings())
	modImplementation("net.fabricmc:fabric-loader:0.18.4")

	// Fabric API
	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
}

tasks {
	processResources {
		inputs.property("version", project.version)
		inputs.property("supported_versions", ">=${project.property("min_supported_version")} <=${project.property("max_supported_version")}")

		filesMatching("fabric.mod.json") {
			expand(
				mutableMapOf(
					"version" to project.version,
					"supported_versions" to ">=${project.property("min_supported_version")} <=${project.property("max_supported_version")}"
				)
			)
		}

		val mixin = if (stonecutter.eval(stonecutter.current.version, ">=1.21"))
			"JukeboxSongPlayerMixin" else "JukeboxBlockEntityMixin"

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

stonecutter {
	replacements.string {
		direction = eval(current.version, ">=1.21.11")
		replace("ResourceLocation", "Identifier")
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