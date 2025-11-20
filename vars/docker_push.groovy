def call(String project, String imageTag, String dockerhubUser) {

    withCredentials([usernamePassword(credentialsId: 'docker', 
                                      usernameVariable: 'REGISTRY_USER', 
                                      passwordVariable: 'REGISTRY_PASS')]) {

        sh """
            echo $REGISTRY_PASS | docker login -u $REGISTRY_USER --password-stdin
            docker push ${REGISTRY_USER}/${project}:${imageTag}
        """
    }
}
