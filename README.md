# Sutr Overview

Installation: Open your Jetbrains IDE (IntelliJ, Webstorm, Pycharm, etc...) and go to Plugins -> Browse Repositories and search for 'Sutr'  

The Sutr plugin can be used to generate the intent.json and utterances required to create Alexa Skills. It defines two files types: __.sutr__ and __.utr__.  

__.sutr__: Used to define sutrs using the language constructs below.  
__.utr__: Used for Amazon style utterance phrases.  
__.json__: Used for intent definition.  

## Sutr Actions

The Sutr plugin adds two actions to the IDE.  
Both are available by right-clicking on the open __.sutr__ file or one or more selected files in the project view.

__Copy Intents__: Creates the Intent.json structure from the selected file(s)  
__Copy Utterances__: Creates a collection of utterances based on the selected file(s)

## Sutr Language

### type
 A type is a Type Name with a comma separated list of values. This is used for creating/referencing Custom Types in the Alexa Skills Kit (ASK)
 syntax: 
 
 ex: 
 
    type LIST_OF_COLORS [
        grey,
        blue, 
        red,
        green,
        yellow
    ]
 

 
### literals
 Used for creating Amazon literal types. Declaration is the word `literal` followed by the name and a comma separated list of phrases.
 
 ex:
 
    literal MyAction [
        stand up,
        walk,
        run,
        jump,
        dance
    ]
 
 Literals are used in Utterances to help build the voice model that is used to extract the literal content from your action.
 
### sutrs
 A Sutr is used to design intents and utterances for your Alexa app.  Declaration is `def` followed by the Intent Name, slot parameters/types, 
 and the list of utterances that could be used to trigger that intent and a function name to trigger within your application
 
 In the example below, *Number*, *MyAction*, and *LIST_OF_COLORS* are slot types, while *count*, *action* and *color* are the names and used in the utterances.  
 The function name *MyApplicationFunction* will be used to generate the launcher code for your application and map the params/intent to that function.
 
 ex:
 
    def MyIntent(Number count, MyAction action, LIST_OF_COLORS color){
        I would like {count} {color} puppies to {action}
        Make {count} {color} puppies {action}
    } => MyApplicationFunction
 
 The combination of the type, literal, and sutr above would generate the following utterances:
 
 
    MyIntent I would like {count} {color} puppies to {stand up|action}
    MyIntent I would like {count} {color} puppies to {walk|action}
    MyIntent I would like {count} {color} puppies to {run|action}
    MyIntent I would like {count} {color} puppies to {jump|action}
    MyIntent I would like {count} {color} puppies to {dance|action}
    MyIntent Make {count} {color} puppies {stand up|action}
    MyIntent Make {count} {color} puppies {walk|action}
    MyIntent Make {count} {color} puppies {run|action}
    MyIntent Make {count} {color} puppies {jump|action}
    MyIntent Make {count} {color} puppies {dance|action}

and the following Intent.json:
    
    {
        "intents": [
            {
                "intent": "MyIntent",
                "slots": [
                    {
                        "name": "count",
                        "type": "AMAZON.NUMBER"
                    },
                    {
                        "name": "action",
                        "type": "AMAZON.LITERAL"
                    },
                    {
                        "name": "color",
                        "type": "LIST_OF_COLORS"
                    }
                ]
            }
        ]
    }


    
