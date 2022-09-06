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

              [$class: 'CascadeChoiceParameter',
                choiceType: 'PT_CHECKBOX',
                description: 'There are some test cases which are marked as skipped for some reason. Select this option to execute skipped test cases.',
                name: 'EXECUTE_SKIPPED_TCS',
                script: [$class: 'GroovyScript',
                script: [
                    classpath: [],
                    sandbox: true,
                    script: """
                        return[
                               'yes': 'Execute Skipped TC(s)'
                          ]
                        """.stripIndent()
                    ]
                ]
            ],

              [$class: 'CascadeChoiceParameter',
                choiceType: 'PT_CHECKBOX',
                description: 'Select test suite(s)',
                name: 'SELECT_ALL',
                script: [$class: 'GroovyScript',
                script: [
                    classpath: [],
                    sandbox: true,
                    script: """
                        return[
                               'select_all': 'Select All:selected'
                          ]
                        """.stripIndent()
                ]
            ]
        ],
              
              [$class: 'CascadeChoiceParameter',
                choiceType: 'PT_CHECKBOX',
                description: 'Select test suite(s)',
                name: 'TESTCASE',
                referencedParameters: 'SELECT_ALL',
                script: [$class: 'GroovyScript',
                fallbackScript: [
                    classpath: [],
                    sandbox: true,
                    script: 'return ["ERROR"]'
                ],
                script: [
                    classpath: [],
                    sandbox: true,
                    script: """
                        if(SELECT_ALL == 'select_all'){
                            return[
                               'testcases/test_login': 'Login:selected',
                               'testcases/connection_with_provider/test_nrt_provider':'Connection with provider -> Nrt provider:selected',
                               'testcases/connection_with_provider/test_rt_provider':'Connection with provider -> RT provider:selected',
                               'testcases/connection_with_provider/test_schedule_provider':'Connection with provider -> Schedule provider:selected',
                               'testcases/connection_with_provider/test_schedule_connection':'Connection with provider -> Schedule Note:selected',
                               'testcases/test_rt_messaging':'RT Offline messaging:selected',
                               'testcases/test_nrt_messaging':'NRT messaging:selected',
                               'testcases/test_eod_feedback':'EOD Feedback (offline):selected',
                               'testcases/text_expanders/test_templates':'TEXT EXPANDERS -> Templates:selected',
                               'testcases/text_expanders/test_macros':'TEXT EXPANDERS -> Macros:selected',
                               'testcases/text_expanders/test_autocorrections':'TEXT EXPANDERS -> Autocorrections:selected',
                               'testcases/text_expanders/test_dictionary':'TEXT EXPANDERS -> Dictionary:selected',
                               'testcases/test_pause_activity':'Pause Activity:selected',
                               'testcases/test_speech_to_text':'Speech To Text:selected',
                               'testcases/NrtS2t/test_speech_to_text_nrt_dictation':'Speech To Text (NRT Dictation):selected',
                               'testcases/NrtS2t/test_speech_to_text_nrt_recording':'Speech To Text (NRT Recording):selected',
                               'testcases/feedback/test_notes_for_grading':'Notes For Grading:selected',
                               'testcases/note_builder/organize_test': 'NoteBuilder -> Organize:selected',
                               'testcases/note_builder/test_build_hpi': 'NoteBuilder -> HPI:selected',
                               'testcases/note_builder/test_build_ap': 'NoteBuilder -> AP:selected',
                               'testcases/note_builder/test_review_tab': 'NoteBuilder -> Review:selected',
                               'testcases/help_support/test_help_support': 'Setting -> Help & Support:selected',
                               'testcases/help_support/test_recover_note_history': 'Setting -> Help & Support -> Recover Note History:selected',
                               'testcases/help_support/test_recover_patient': 'Setting -> Help & Support -> Recover Patient:selected'
                          ]
                            
                        }else{
                            return[
                               'testcases/test_login': 'Login',
                               'testcases/connection_with_provider/test_nrt_provider':'Connection with provider -> Nrt provider',
                               'testcases/connection_with_provider/test_rt_provider':'Connection with provider -> RT provider',
                               'testcases/connection_with_provider/test_schedule_provider':'Connection with provider -> Schedule provider',
                               'testcases/connection_with_provider/test_schedule_connection':'Connection with provider -> Schedule Note',
                               'testcases/test_rt_messaging':'RT Offline messaging',
                               'testcases/test_nrt_messaging':'NRT messaging',
                               'testcases/test_eod_feedback':'EOD Feedback (offline)',
                               'testcases/text_expanders/test_templates':'TEXT EXPANDERS -> Templates',
                               'testcases/text_expanders/test_macros':'TEXT EXPANDERS -> Macros',
                               'testcases/text_expanders/test_autocorrections':'TEXT EXPANDERS -> Autocorrections',
                               'testcases/text_expanders/test_dictionary':'TEXT EXPANDERS -> Dictionary',
                               'testcases/test_pause_activity':'Pause Activity',
                               'testcases/test_speech_to_text':'Speech To Text',
                               'testcases/NrtS2t/test_speech_to_text_nrt_dictation':'Speech To Text (NRT Dictation)',
                               'testcases/NrtS2t/test_speech_to_text_nrt_recording':'Speech To Text (NRT Recording)',
                               'testcases/feedback/test_notes_for_grading':'Notes For Grading',
                               'testcases/note_builder/organize_test': 'NoteBuilder -> Organize',
                               'testcases/note_builder/test_build_hpi': 'NoteBuilder -> HPI',
                               'testcases/note_builder/test_build_ap': 'NoteBuilder -> AP',
                               'testcases/note_builder/test_review_tab': 'NoteBuilder -> Review',
                               'testcases/help_support/test_help_support': 'Setting -> Help & Support',
                               'testcases/help_support/test_recover_note_history': 'Setting -> Help & Support -> Recover Note History',
                               'testcases/help_support/test_recover_patient': 'Setting -> Help & Support -> Recover Patient'
                          ]
                        }
                        """.stripIndent()
                ]
            ]
        ]
        

          ])
      ]
  )

  node {
      try{
        //def repoInformation = checkout scm
        //def GIT_COMMIT_HASH = repoInformation.GIT_COMMIT

        def parallelTestConfiguration = [
          [
          '[Login]': 'testcases/test_login',
          '[Connection with provider: Nrt provider]': 'testcases/connection_with_provider/test_nrt_provider',
          '[Connection with provider: RT provider]': 'testcases/connection_with_provider/test_rt_provider',
          '[Connection with provider: Schedule provider]': 'testcases/connection_with_provider/test_schedule_provider',
          '[Connection with provider: Schedule Note]': 'testcases/connection_with_provider/test_schedule_connection',
          '[RT Offline messaging]': 'testcases/test_rt_messaging',
          '[NRT messaging]': 'testcases/test_nrt_messaging',
          '[EOD Feedback (offline)]': 'testcases/test_eod_feedback',
          '[TEXT EXPANDERS:Test Templates]': 'testcases/text_expanders/test_templates',
          '[TEXT EXPANDERS:Test Macros]': 'testcases/text_expanders/test_macros',
          '[TEXT EXPANDERS:Test Autocorrections]': 'testcases/text_expanders/test_autocorrections',
          '[TEXT EXPANDERS:Test Dictionary]': 'testcases/text_expanders/test_dictionary',
          '[Pause Activity]': 'testcases/test_pause_activity',
          '[S2T Live]': 'testcases/test_speech_to_text',
          '[S2T -> NRT Dictation]': 'testcases/NrtS2t/test_speech_to_text_nrt_dictation',
          '[S2T -> NRT Recording]': 'testcases/NrtS2t/test_speech_to_text_nrt_recording',
          '[Feedback -> Test Notes For Grading]': 'testcases/feedback/test_notes_for_grading',
          '[Feedback -> Test Notes For Grading Regression]': 'testcases/feedback/test_notes_for_grading_regression',
          '[NoteBuilder -> Organize]': 'testcases/note_builder/organize_test',
          '[NoteBuilder -> HPI]': 'testcases/note_builder/test_build_hpi',
          '[NoteBuilder -> AP]': 'testcases/note_builder/test_build_ap',
          '[NoteBuilder -> Review]': 'testcases/note_builder/test_review_tab',
          '[Settings -> Help & Support]': 'testcases/help_support/test_help_support',
          '[Settings -> Help & Support -> Recover Note History]': 'testcases/help_support/test_recover_note_history',
          '[Settings -> Help & Support -> Recover Patient]': 'testcases/help_support/test_recover_patient'
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
          results: [[path: 'target/allure-results']]
        ])


        stage("G-chat notifier"){
          def props = readProperties  file: 'resources/jenkins.properties'
    
          def gchat_webhook_link = props['gchat_webhook_link']

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


          def summary = junit testResults: 'testResults/**/*.xml'
          def totalFailed = summary.failCount
          def totalCount = summary.totalCount
          double percentFailed = totalFailed * 100 / totalCount

          def summaryMsg = ""

          if(totalFailed){
              summaryMsg = "${summary.failCount} (${percentFailed.round(2)}%) failed out of ${summary.totalCount} test cases.\n\n"
          }else{
              summaryMsg = "All passed (out of ${totalCount})!!!\n\n"
          }


          def startMessage = "*ScribePortalAutomation script execution is completed. Please see the details:*\n*------------------------------------------------------------------------------------------------------------------------------------------*\n\n"
          def buildStatus = "*Build status:* " + currentBuild.result + "\n"
          def buildNumber = "*Build number:* " + currentBuild.number + "\n"
          def buildInitiatedBy = "*Build Initiated by:* ${jobUserId}\n"
          def buildStarted = "*Build started:* " + env.BUILD_TIMESTAMP + "\n"
          def buildEnded = "*Build ended:* " + now.format("YYYY-MM-dd HH:mm:ss", TimeZone.getTimeZone('BST')) + " BDT\n"
          def buildDuration = "*Duration:* " + currentBuild.durationString + "\n\n"
          def testType = "*Test type:* ${TESTTYPE}\n"
          def testENV = "*Test ENV:* ${ENV}\n"
          def testURL = "*Test URL:* ${params.URL}\n"
          def buildURL = "*Build URL:* ${JOB_URL}\n"
          def reportLink = "*Report URL:* ${BUILD_URL}allure/\n\n"

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
    String [] testcaseList = testcase.split(',')
    def parallelSteps = [:]

    wrap([$class: 'BuildUser']) {
        jobUserId = "${BUILD_USER_ID}"
    }

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
          def EXECUTE_SKIPPED = params.EXECUTE_SKIPPED_TCS
          if(params.EXECUTE_SKIPPED_TCS != 'yes'){
              EXECUTE_SKIPPED = 'no'
          }
          print("Test Val: ${EXECUTE_SKIPPED}")
          println("python3 -m pytest --env=${ENV} ${file}.py -m ${params.TESTTYPE.toLowerCase()} --run-skipped=${EXECUTE_SKIPPED} --junitxml=${WORKSPACE}/testResults/${file}.xml --enable-jenkins=yes --alluredir=${WORKSPACE}/allure-results -rA *******************")
      }
    }
  }
