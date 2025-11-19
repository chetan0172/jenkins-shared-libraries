def call() {

    echo "------ OWASP Dependency Check: Installing Dependencies ------"

    // Install backend dependencies
    if (fileExists("backend/package.json")) {
        dir("backend") {
            sh "npm install"
        }
    }

    // Install frontend dependencies
    if (fileExists("frontend/package.json")) {
        dir("frontend") {
            sh "npm install"
        }
    }

    // Install root dependencies (if any)
    if (fileExists("package.json")) {
        sh "npm install"
    }

    echo "------ Running OWASP Dependency Check via Plugin ------"

    dependencyCheck(
        odcInstallation: 'OWASP',
        additionalArguments: '''
            --scan ./ \
            --disableNodeAudit \
            --format XML
        ''',
        outdir: './'
    )

    echo "------ Publishing OWASP Dependency Check Report ------"

    dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
}
