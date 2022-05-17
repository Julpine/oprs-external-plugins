import ProjectVersions.openosrsVersion

version = "0.0.11"

project.extra["PluginName"] = "Clan Pile Indicators" // This is the name that is used in the external plugin manager panel
project.extra["PluginDescription"] = "Clan Pile Indicators" // This is the description that is used in the external plugin manager panel


tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}