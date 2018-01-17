define({ "api": [
  {
    "type": "get",
    "url": "/plugins/description",
    "title": "The current played plugin description",
    "name": "PluginsDescription",
    "group": "Plugins",
    "version": "0.1.0",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>The plugin description</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Example data on success",
          "content": "{\n    \"description\": \"Plugin description\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/PluginServices.java",
    "groupTitle": "Plugins"
  },
  {
    "type": "get",
    "url": "/plugins/list",
    "title": "The list of available plugins",
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
          "content": "[{\n    \"type\": \"polytech.teamf.plugins.CaesarCipherPlugin\",\n    \"args\":\n    [\n    \"description\",\n    \"plain_text\",\n    \"cipher_padding\"\n    ]\n}]",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/PluginServices.java",
    "groupTitle": "Plugins"
  },
  {
    "type": "get",
    "url": "/plugins/status",
    "title": "The current played plugin status",
    "name": "PluginsStatus",
    "group": "Plugins",
    "version": "0.1.0",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "<p>The plugin status</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Example data on success",
          "content": "{\n    \"status\": \"true\"\n}",
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
    "title": "Instantiate the runner",
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
          "content": "[{\n    \"type\": \"CaesarCipherPlugin\",\n    \"description\": \"Put a description here\",\n    \"plain_text\": \"Put the text to cipher here\",\n    \"cipher_padding\": \"5\"\n}]",
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
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  },
  {
    "type": "post",
    "url": "/runners/answer",
    "title": "Give an answer to solve the current step and retrieve the result",
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
          "title": "Request-Example (Be careful, a request is plugin specific):",
          "content": "{\n    \"attempt_text\": \"Put your answer here\"\n}",
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
            "description": "<p>The answer was empty. <code>1</code> answer has to be given.</p>"
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
    "url": "/runners/status",
    "title": "Retrieve the status for the next step of the game.",
    "name": "RunnerHasNext",
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
          "title": "Example data on success",
          "content": "{\n    \"status\": \"ok\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/RunnerServices.java",
    "groupTitle": "Runners"
  },
  {
    "type": "get",
    "url": "/services/{service}",
    "title": "Doc here",
    "name": "Service",
    "group": "Services",
    "version": "0.1.0",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "service",
            "description": "<p>Doc here</p> <p>http://localhost:8080/services/GoogleSheetsService?gsheet=%22https://docs.google.com/spreadsheets/d/17d4SfrjbdyPq9x8HEjumkfYN8b_aBPwaIHVFJnNqbG0/gviz/tq?tqx=out:csv%22</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Example data on success",
          "content": "{\n    \"example\": \"example\"\n}",
          "type": "json"
        }
      ]
    },
    "filename": "polyescape_engine/src/polytech/teamf/api/ServiceServices.java",
    "groupTitle": "Services"
  }
] });
