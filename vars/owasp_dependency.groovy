def call() {

    echo "------ OWASP Dependency Check: Installing Dependencies ------"

    nodejs('Node18') {   // <-- THIS IS REQUIRED

        echo "Node Version:"
        sh "node -v || true"

        echo "NPM Version:"
        sh "npm -v || true"

        // Backend dependencies
        if (fileExists("backend/package.json")) {
            dir("backend") {
                sh "npm install"
            }
        }

        // Frontend dependencies
        if (fileExists("frontend/package.json")) {
            dir("frontend") {
                sh "npm install"
            }
        }

        // Root dependencies
        if (fileExists("package.json")) {
            sh "npm install"
        }

        echo "------ Running OWASP Dependency Check ------"

        dependencyCheck(
            odcInstallation: 'OWASP',
            additionalArguments: '''
                --scan ./ \
                --disableNodeAudit \
                --format XML
            ''',
            outdir: './'
        )

        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
    } // END nodejs block
}
