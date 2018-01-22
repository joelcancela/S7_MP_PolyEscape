'use strict';

/**
 * @ngdoc service
 * @name ChangelogLoaderService
 * @description Service used to retrieve ROCKFlows updates from changelog.json
 */

angular.module('polyEscapeApp')
  .service('TranslatorService', function () {

      this.translator = {
        description: "Description/Question de l'épreuve",
        answers_csv: "Réponses possibles séparées par des \",\"",
        correct_answers_csv: "Réponses correctes séparées par des \",\"",
        plain_text: "Réponse",
        cipher_padding: "Longueur du décalage",
        url: "URL",
        CaesarCipherPlugin: "Epreuve Code César",
        SimplePasswordPlugin: "Epreuve Question simple",
        MultipleChoiceQuestionPlugin: "Epreuve QCM",
        'polytech.teamf.plugins.EmailSpyPlugin': "Epreuve avec Réponse par mail",
        'polytech.teamf.plugins.GoogleSheetsPlugin':"Epreuve avec QCM Google Sheets"
      };

      this.translate = function(str){
        return this.translator[str] || str;
      };

  });
