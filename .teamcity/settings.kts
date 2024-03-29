import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2023.05"

project {

    buildType(TestBuildConfiguration)
}

object TestBuildConfiguration : BuildType({
    name = "TestBuildConfiguration"

    artifactRules = "target => what_a_target"

    params {
        select("Environment", "", display = ParameterDisplay.PROMPT,
                options = listOf("Stage", "Pre", "Prod"))
        checkbox("SirenaNotificationEnable", "true", label = "Sirena notifications enabled", display = ParameterDisplay.PROMPT,
                  checked = "true", unchecked = "false")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {

            conditions {
                equals("Environment", "Stage")
                equals("SirenaNotificationEnable", "true")
            }
            goals = "clean test"
            coverageEngine = idea {
                includeClasses = "main.*"
            }
        }
        script {
            name = "Hello"
            executionMode = BuildStep.ExecutionMode.ALWAYS

            conditions {
                equals("Environment", "Stage")
            }
            scriptContent = """
                curl --data "{\"email\": \"%teamcity.build.triggeredBy.username%\"}" \
                        --header "Content-Type: application/json" \
                        --header "Token: %env.SIRENA_TOKEN%" \
                        --request POST \
                        "%env.SIRENA_URL%"
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
