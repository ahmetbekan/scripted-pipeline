properties([
    parameters([
        string(defaultValue: '', description: 'Enter IP', name: 'node', trim: true)
        ])
    ])

node{
    stage("Pull Repo"){
        git url: 'https://github.com/ahmetbekan/ansible-spring_petclinic.git'
    }

    stage("Install Prerequisites"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins-Master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'prerequisites.yml'
    }
    withEnv(['PETCLINIC_REPO=https://github.com/ikambarov/spring-petclinic.git', 'PETCLINIC_BRANCH=master']) {
        stage("Pull Repo"){
            ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins-Master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'pull-repo.yml'
        }
    }
    stage("Install Java"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins-Master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'install-java.yml'
    }
    stage("Install Maven"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins-Master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'install-mvnw.yml'
    }
    stage("Start App"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins-Master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'start-app.yml'
    }
    stage("Start App Java"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'Jenkins-Master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'start-app-java.yml'
    } 
}
