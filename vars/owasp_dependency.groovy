def call() {

    echo "------ OWASP Dependency Check: Installing Dependencies ------"

    nodejs('Node18') {

        // Install dependencies
        if (fileExists("backend/package.json")) {
            dir("backend") { sh "npm install" }
        }

        if (fileExists("frontend/package.json")) {
            dir("frontend") { sh "npm install" }
        }

        if (fileExists("package.json")) {
            sh "npm install"
        }

        echo "------ Running OWASP Dependency Check ------"

        dependencyCheck(
            odcInstallation: 'OWASP',
            additionalArguments: '''
                --scan ./ \
                --disableNodeAudit \
                --disableAssembly \
                --disableMSBuild \
                --format XML
            '''
        )

        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
    }
}
