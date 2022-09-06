properties(
      [
          pipelineTriggers([cron('* * * * *')]),
            
      
          parameters([
              choice(name: 'ENV', choices: ['staging', 'dev', 'prod'], description: 'Environment to run test cases.'),
                

              [$class: 'CascadeChoiceParameter',
                  choiceType: 'PT_SINGLE_SELECT',
                  description: 'Select a URL',
                  name: 'URL',
                  referencedParameters: 'ENV',
                  script: [$class: 'GroovyScript',
                      fallbackScript: [
                          classpath: [],
                          sandbox: true,
                          script: 'return ["https://staging-scribe.augmedix.com/"]'
                      ],
                      script: [
                          classpath: [],
                          sandbox: true,
                          script: """
                              if (ENV == 'dev') {
                                  return[
                                          'https://dev-scribe1.augmedix.com':'DEV-1 [https://dev-scribe1.augmedix.com]',
                                          'https://dev-scribe2.augmedix.com':'DEV-2 [https://dev-scribe2.augmedix.com]',
                                          'https://dev-scribe3.augmedix.com':'DEV-3 [https://dev-scribe3.augmedix.com]',
                                          'https://dev-scribe4.augmedix.com':'DEV-4 [https://dev-scribe4.augmedix.com]',
                                          'https://dev-scribe5.augmedix.com':'DEV-5 [https://dev-scribe5.augmedix.com]',
                                          'https://dev-scribe6.augmedix.com':'DEV-6 [https://dev-scribe6.augmedix.com]:selected'
                                      ]
                              }
                              else if(ENV == 'staging'){
                                  return ['https://staging-scribe.augmedix.com/:selected': 'STAGING [https://staging-scribe.augmedix.com/]']
                              }else{
                                  return ['': 'Not supported yet!']
                              }
                          """.stripIndent()
                      ]
                  ]
              ],

              choice(name: 'TESTTYPE', choices: ['SANITY', 'REGRESSION'], description: 'Select any of the testing types.'),

        ]

          ])
      ]
  )

  node {
      try{
        def repoInformation = checkout scm
        def GIT_COMMIT_HASH = repoInformation.GIT_COMMIT
            
        

        def parallelTestConfiguration = [
          [
            env.testcase_list_ui_checkbox
          ]
        ]

        def stepList = prepareBuildStages(parallelTestConfiguration)

        for (def groupOfSteps in stepList) {
          parallel groupOfSteps
        }

        currentBuild.result = "SUCCESS"
      } catch(error) {
        currentBuild.result = "FAILURE"
        echo "The following error occurred: ${error}"
        throw error
      } finally {

        allure([
          includeProperties: false,
          jdk: '',
          properties: [],
  //         reportBuildPolicy: 'ALWAYS',
          results: [[path: 'target/allure-results']]
        ])


        stage("G-chat notifier"){
              
          def props = readProperties  file: 'resources/jenkins.properties'
    
          def gchat_webhook_link = props['gchat_webhook']

          wrap([$class: 'BuildUser']) {
              jobUserId = "${BUILD_USER_ID}"
              jobUserName = "${BUILD_USER}"
          }

          def now = new Date()

          if(jobUserId == "timer"){
            jobUserId = "SCHEDULER"
          }else{
            jobUserId = jobUserId.toUpperCase()
          }


//           def summary = junit testResults: 'testResults/**/*.xml'
//           def totalFailed = summary.failCount
//           def totalCount = summary.totalCount
//           double percentFailed = totalFailed * 100 / totalCount

//           def summaryMsg = ""

//           if(totalFailed){
//               summaryMsg = "${summary.failCount} (${percentFailed.round(2)}%) failed out of ${summary.totalCount} test cases.\n\n"
//           }else{
//               summaryMsg = "All passed (out of ${totalCount})!!!\n\n"
//           }


          def startMessage = "ScribePortalAutomation script execution is completed. Please see the details:\n------------------------------------------------------------------------------------------------------------------------------------------\n\n"
          def summaryMsg = "<span style=\"color:red\">BUILD FAILED</span>"
          def buildStatus = "Build status: " + currentBuild.result + "\n"
          def buildNumber = "Build number: " + currentBuild.number + "\n"
          def buildInitiatedBy = "*Build Initiated by:* ${jobUserId}\n"
          def buildStarted = "Build started: " + env.BUILD_TIMESTAMP + "\n"
          def buildEnded = "Build ended: " + now.format("YYYY-MM-dd HH:mm:ss", TimeZone.getTimeZone('BST')) + " BDT\n"
          def buildDuration = "Duration: " + currentBuild.durationString + "\n\n"
          def testType = "Test type: ${TESTTYPE}\n"
          def testENV = "Test ENV: ${ENV}\n"
          def testURL = "Test URL: ${params.URL}\n"
          def buildURL = "Build URL: ${JOB_URL}\n"
          def reportLink = "Report URL: ${BUILD_URL}allure/\n\n"

          def message = startMessage + summaryMsg + buildStatus + buildNumber + buildInitiatedBy + buildStarted + buildEnded + buildDuration + testType + testENV + testURL + buildURL + reportLink


          googlechatnotification message: message, url: gchat_webhook_link
        }
      }
  }


  def prepareBuildStages(List<Map<String,String>> parallelTestConfiguration) {
    def stepList = []

    println('Preparing builds...')

    for (def parallelConfig in  parallelTestConfiguration) {
      def parallelSteps = prepareParallelSteps(parallelConfig)
      stepList.add(parallelSteps)
    }

    println(stepList)
    println('Finished preparing builds!')

    return stepList
  }


  def prepareParallelSteps(Map<String, String> parallelStepsConfig) {
    def testcases = params.TESTCASE
    println("Data Types: " + testcases.getClass())
    String [] testcaseList = testcase.split(',')
    def parallelSteps = [:]
    
    println("Testcases: " + testcases + "****************************************************")
        
    
     
     wrap([$class: 'BuildUser']) {
              jobUserId = "${BUILD_USER_ID}"
              jobUserName = "${BUILD_USER}"
      }
        
        println("Job Runner: ${jobUserId}******************************************************!!!!!!!!!!!!!!!!!!!!!!!!!!!")

    if(jobUserId != "timer"){
      for(def key in testcaseList){
        def tmp = key.split('/')
        def suiteName = tmp[tmp.size() - 1]
        parallelSteps.put(suiteName, prepareOneBuildStage(suiteName, key))
      }
    }else{
        for (def key in parallelStepsConfig.keySet()) {
        parallelSteps.put(key, prepareOneBuildStage(key, parallelStepsConfig[key]))
    }
    }

    return parallelSteps
  }


  def prepareOneBuildStage(String name, String file) {
    return {
      stage("Test: ${name}") {
        println("Test: ${name}")
            println("FileName: ${file}")
      }
    }
  }
