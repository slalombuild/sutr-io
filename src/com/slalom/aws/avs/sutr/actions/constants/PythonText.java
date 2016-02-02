package com.slalom.aws.avs.sutr.actions.constants;

/**
 * Created by Stryder on 1/24/2016.
 */
public class PythonText {

    public static String OnIntentPreamble =
            "\ndef on_intent(intent_request, session):\n" +
            "    \"\"\" Called when the user specifies an intent for this skill \"\"\"\n" +
            "\n" +
            "    print(\"on_intent requestId=\" + intent_request['requestId'] +\n" +
            "          \", sessionId=\" + session['sessionId'])\n" +
            "\n" +
            "    intent_name = intent_request['intent']['name']\n" +
            "    intent = intent_request['intent']\n" +
            "\n" +
            "    if 'slots' not in intent:\n" +
            "        intent['slots'] = dict()\n" +
            "\n"
            ;

    public static String OnIntentAfterword = "";

    public static String GeneratorPreamble = "\"\"\"\n" +
            "This sample demonstrates a simple skill built with the Amazon Alexa Skills Kit.\n" +
            "The Intent Schema, Custom Slots, and Sample Utterances for this skill, as well\n" +
            "as testing instructions are located at http://amzn.to/1LzFrj6\n" +
            "\n" +
            "For additional samples, visit the Alexa Skills Kit Getting Started guide at\n" +
            "http://amzn.to/1LGWsLG\n" +
            "\"\"\"\n" +
            "\n" +
            "from __future__ import print_function\n" +
            "\n" +
            "def lambda_handler(event, context):\n" +
            "    \"\"\" Route the incoming request based on type (LaunchRequest, IntentRequest,\n" +
            "    etc.) The JSON body of the request is provided in the event parameter.\n" +
            "    \"\"\"\n" +
            "    print(\"event.session.application.applicationId=\" +\n" +
            "          event['session']['application']['applicationId'])\n" +
            "\n" +
            "    \"\"\"\n" +
            "    Uncomment this if statement and populate with your skill's application ID to\n" +
            "    prevent someone else from configuring a skill that sends requests to this\n" +
            "    function.\n" +
            "    \"\"\"\n" +
            "    # if (event['session']['application']['applicationId'] !=\n" +
            "    #         \"amzn1.echo-sdk-ams.app.[unique-value-here]\"):\n" +
            "    #     raise ValueError(\"Invalid Application ID\")\n" +
            "\n" +
            "    if event['session']['new']:\n" +
            "        on_session_started({'requestId': event['request']['requestId']},\n" +
            "                           event['session'])\n" +
            "\n" +
            "    if event['request']['type'] == \"LaunchRequest\":\n" +
            "        return on_launch(event['request'], event['session'])\n" +
            "    elif event['request']['type'] == \"IntentRequest\":\n" +
            "        return on_intent(event['request'], event['session'])\n" +
            "    elif event['request']['type'] == \"SessionEndedRequest\":\n" +
            "        return on_session_ended(event['request'], event['session'])\n" +
            "\n" +
            "\n" +
            "def get_welcome_response():\n" +
            "    \"\"\" If we wanted to initialize the session to have some attributes we could\n" +
            "    add those here\n" +
            "    \"\"\"\n" +
            "\n" +
            "    session_attributes = {}\n" +
            "    card_title = \"Welcome\"\n" +
            "    speech_output = \"Welcome to the Alexa Skills Kit sample. \" \\\n" +
            "                    \"Please tell me your favorite color by saying, \" \\\n" +
            "                    \"my favorite color is red\"\n" +
            "    # If the user either does not reply to the welcome message or says something\n" +
            "    # that is not understood, they will be prompted again with this text.\n" +
            "    reprompt_text = \"Please tell me your favorite color by saying, \" \\\n" +
            "                    \"my favorite color is red.\"\n" +
            "    should_end_session = False\n" +
            "    return build_response(session_attributes, build_speechlet_response(\n" +
            "        card_title, speech_output, reprompt_text, should_end_session))\n" +
            "\n" +
            "\n" +
            "def on_session_started(session_started_request, session):\n" +
            "    \"\"\" Called when the session starts \"\"\"\n" +
            "\n" +
            "    print(\"on_session_started requestId=\" + session_started_request['requestId']\n" +
            "          + \", sessionId=\" + session['sessionId'])\n" +
            "\n" +
            "\n" +
            "def on_launch(launch_request, session):\n" +
            "    \"\"\" Called when the user launches the skill without specifying what they\n" +
            "    want\n" +
            "    \"\"\"\n" +
            "\n" +
            "    print(\"on_launch requestId=\" + launch_request['requestId'] +\n" +
            "          \", sessionId=\" + session['sessionId'])\n" +
            "    # Dispatch to your skill's launch\n" +
            "\n" +
            "    return get_welcome_response()\n" +
            "\n" +
            "\n" +
            "def on_session_ended(session_ended_request, session):\n" +
            "    \"\"\" Called when the user ends the session.\n" +
            "\n" +
            "    Is not called when the skill returns should_end_session=true\n" +
            "    \"\"\"\n" +
            "    print(\"on_session_ended requestId=\" + session_ended_request['requestId'] +\n" +
            "          \", sessionId=\" + session['sessionId'])\n" +
            "    # add cleanup logic here\n" +
            "\n" +
            "\n" +
            "# --------------- Helpers that build all of the responses ----------------------\n" +
            "\n" +
            "\n" +
            "def build_speechlet_response(title, output, reprompt_text, should_end_session):\n" +
            "    return {\n" +
            "        'outputSpeech': {\n" +
            "            'type': 'PlainText',\n" +
            "            'text': output\n" +
            "        },\n" +
            "        'card': {\n" +
            "            'type': 'Simple',\n" +
            "            'title': 'SessionSpeechlet - ' + title,\n" +
            "            'content': 'SessionSpeechlet - ' + output\n" +
            "        },\n" +
            "        'reprompt': {\n" +
            "            'outputSpeech': {\n" +
            "                'type': 'PlainText',\n" +
            "                'text': reprompt_text\n" +
            "            }\n" +
            "        },\n" +
            "        'shouldEndSession': should_end_session\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "def build_response(session_attributes, speechlet_response):\n" +
            "    return {\n" +
            "        'version': '1.0',\n" +
            "        'sessionAttributes': session_attributes,\n" +
            "        'response': speechlet_response\n" +
            "    }\n" +
            "\n";
}
