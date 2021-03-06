properties([
    parameters([
        string(defaultValue: '', description: 'Input node IP', name: 'SSHNODE', trim: true)
        ])
    ])
node {
    withCredentials([sshUserPrivateKey(credentialsId: 'Jenkins-Master', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
        stage("Initialize") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install epel-release -y "
        }
        stage("Install java") { 
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install java-1.8.0-openjdk-devel -y" 
        } 
        stage("Install git") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install git -y"
        }
        stage("Install git") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } 'rm -rf spring-petclinic && git clone https://github.com/spring-projects/spring-petclinic.git && ls'"
        }
        stage("Install git") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } 'cd spring-petclinic/ && ./mvnw package'"
        }
        stage("Install git") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } 'cd spring-petclinic &&  mv target/*.jar target/app.jar'"
        }
        stage("Install git") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } java -jar spring-petclinic/target/app.jar"
        }
    }   
}
