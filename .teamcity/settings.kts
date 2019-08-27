import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.VisualStudioStep
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.nuGetInstaller
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.visualStudio
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

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

version = "2019.1"

project {

    vcsRoot(HttpsGithubComHarikrishnanaGadgeonASPFormsGitRefsHeadsMaster)

    buildType(Build)
    buildType(Build1)
}

object Build : BuildType({
    name = "Build"

    enablePersonalBuilds = false
    maxRunningBuilds = 1
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(HttpsGithubComHarikrishnanaGadgeonASPFormsGitRefsHeadsMaster)
    }

    steps {
        visualStudio {
            path = "HelloPrint/HelloPrint.sln"
            version = VisualStudioStep.VisualStudioVersion.vs2017
            runPlatform = VisualStudioStep.Platform.x86
            msBuildVersion = VisualStudioStep.MSBuildVersion.V15_0
            msBuildToolsVersion = VisualStudioStep.MSBuildToolsVersion.V15_0
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_CUSTOM
            quietPeriod = 30
            branchFilter = ""
        }
    }
})

object Build1 : BuildType({
    name = "Build (1)"

    artifactRules = """HelloPrint\HelloPrint\bin => hari"""

    params {
        param("teamcity.tool.NuGet.CommandLine.DEFAULT", "%teamcity.tool.NuGet.CommandLine.4.6.2%")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        nuGetInstaller {
            toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
            projects = "HelloPrint/HelloPrint.sln"
        }
        visualStudio {
            path = "simpleTest/simpleTest.sln"
            version = VisualStudioStep.VisualStudioVersion.vs2017
            runPlatform = VisualStudioStep.Platform.x86
            msBuildVersion = VisualStudioStep.MSBuildVersion.V15_0
            msBuildToolsVersion = VisualStudioStep.MSBuildToolsVersion.V15_0
        }
        visualStudio {
            path = "HelloPrint/HelloPrint.sln"
            version = VisualStudioStep.VisualStudioVersion.vs2017
            runPlatform = VisualStudioStep.Platform.x86
            msBuildVersion = VisualStudioStep.MSBuildVersion.V15_0
            msBuildToolsVersion = VisualStudioStep.MSBuildToolsVersion.V15_0
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComHarikrishnanaGadgeonASPFormsGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/harikrishnana-gadgeon/ASPForms.git#refs/heads/master"
    url = "https://github.com/harikrishnana-gadgeon/ASPForms.git"
    authMethod = password {
        userName = "harikrishnana-gadgeon"
        password = "credentialsJSON:ea3ccddc-d80e-482d-9530-204baeeff92c"
    }
})
