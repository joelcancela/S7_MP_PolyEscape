define({ "api": [
  {
    "type": "get",
    "url": "/plugins/list",
    "title": "Retrieve the list of available plugins",
    "name": "PluginsList",
    "group": "Plugins",
    "version": "0.1.0",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "plugins",
            "description": "<p>The plugins list</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Example data on success",
          "content": "[\n  {\n    \"name\": \"CaesarCipherPlugin\",\n    \"serviceDependencies\": [\n    \"polytech.teamf.services.CaesarCipherService\"\n    ],\n    \"pluginDependencies\": [],\n    \"args\": {\n      \"plain_text\": \"java.lang.String\",\n      \"description\": \"java.lang.String\",\n      \"cipher_padding\": \"java.lang.Integer\"\n    },\n    \"schema\": {\n      \"attempt\": \"java.lang.String\"\n    }\n  }\n]",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/PluginServices.java",
    "groupTitle": "Plugins"
  },
  {
    "type": "put",
    "url": "/runners/instantiate",
    "title": "Instantiate a new unique runner instance",
    "name": "InstantiateRunner",
    "group": "Runners",
    "version": "0.1.0",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "config",
            "description": "<p>The plugins configuration</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "[{\n  \"name\": \"CaesarCipherPlugin\",\n  \"args\": {\n    \"plain_text\": \"a\",\n    \"description\": \"d\",\n    \"cipher_padding\": 7\n  }\n}]",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "EmptyConfiguration",
            "description": "<p>The configuration was empty. Minimum of <code>1</code> plugin configuration is required.</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Example data on success",
          "content": "{\n  \"id\": \"872ac411-39ec-4674-922e-3a51b7ba522d\",\n  \"status\": null\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  },
  {
    "type": "post",
    "url": "/runners/{id}/answer",
    "title": "Give an answer to solve the current step and retrieve the result on the runner with id {id}",
    "name": "RunnerAnswer",
    "group": "Runners",
    "version": "0.1.0",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "answer",
            "description": "<p>The player's answer</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"attempt\": \"Put your answer here\"\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "EmptyAnswer",
            "description": "<p>The answer was empty. Exactly <code>1</code> answer has to be given.</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Example data on success",
          "content": "{\n    \"success\": \"true\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  },
  {
    "type": "get",
    "url": "/runners/{id}/description",
    "title": "Get the current played plugin description on the runner with id {id}",
    "name": "RunnerDescription",
    "group": "Runners",
    "version": "0.1.0",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>The current played plugin description</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Example data on success",
          "content": "{\n     \"attributes\": {\n          \"name\": \"CaesarCipherPlugin\",\n          \"description\": \"a Voici le code à décrypter: K\"\n     },\n     \"responseFormat\": {\n         \"attempt\": \"java.lang.String\"\n     }\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  },
  {
    "type": "get",
    "url": "/runners/{id}/inputStatus",
    "title": "Get the status for the current step on the runner with id {id}",
    "name": "RunnerInputStatus",
    "group": "Runners",
    "version": "0.1.0",
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "EmptyAnswer",
            "description": "<p>The answer was empty. Exactly <code>1</code> answer has to be given.</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Example data on success, this request is mostly used when the step is using a service that's calls our API. (\"Input\" services like mails)",
          "content": "{\n    \"success\": \"true\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  },
  {
    "type": "get",
    "url": "/runners/{id}/status",
    "title": "Retrieve the status for the next step of the game on the runner with id {id}",
    "name": "RunnerStatus",
    "group": "Runners",
    "version": "0.1.0",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "200",
            "description": "<p>{String} status The current runner status</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Example data on success: (the status can be ok if a correct answer has been given and there's another step after, finish if a correct answer has been given and there's no more step or forbidden if no correct answer has been given (the player tries to skip the step))",
          "content": "{\n\"id\": null,\n\"status\": \"ok/finish/forbidden\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  }
] });
